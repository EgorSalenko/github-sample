package io.esalenko.github.sample.app.ui.search

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.GenericItemAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.mikepenz.fastadapter.diff.FastAdapterDiffUtil
import com.mikepenz.fastadapter.listeners.ClickEventHook
import com.mikepenz.fastadapter.scroll.EndlessRecyclerOnScrollListener
import com.mikepenz.itemanimators.SlideLeftAlphaAnimator
import io.esalenko.github.sample.app.R
import io.esalenko.github.sample.app.data.db.entity.SearchItemEntity
import io.esalenko.github.sample.app.helper.launchBrowser
import io.esalenko.github.sample.app.ui.common.BaseFragment
import io.esalenko.github.sample.app.ui.common.LiveDataResult
import io.esalenko.github.sample.app.ui.common.LiveDataResult.*
import io.esalenko.github.sample.app.ui.search.adapter.ItemSearch
import io.esalenko.github.sample.app.ui.search.adapter.ProgressItem
import kotlinx.android.synthetic.main.fragment_search.*
import org.koin.android.viewmodel.ext.android.viewModel


class SearchFragment : BaseFragment(R.layout.fragment_search) {

    companion object {
        const val TAG = "SearchFragment"
        fun newInstance() = SearchFragment()
    }

    private val searchViewModel by viewModel<SearchViewModel>()

    private lateinit var fastAdapter: FastAdapter<ItemSearch>
    private lateinit var itemAdapter: GenericItemAdapter
    private lateinit var footerAdapter: GenericItemAdapter

    private lateinit var endlessScrollListener: EndlessRecyclerOnScrollListener

    override fun onReady(savedInstanceState: Bundle?) {
        super.onReady(savedInstanceState)
        retainInstance = true
        subscribeUi()
    }

    override fun onInitView(savedInstanceState: Bundle?) {
        super.onInitView(savedInstanceState)
        listeners()
        recyclerView()
    }

    private fun recyclerView() {
        itemAdapter = ItemAdapter.items()
        footerAdapter = ItemAdapter.items()
        fastAdapter = FastAdapter.with(listOf(itemAdapter, footerAdapter))

        searchResultList.apply {
            adapter = fastAdapter
            layoutManager = LinearLayoutManager(context)
            itemAnimator = SlideLeftAlphaAnimator()
            endlessScrollListener = object : EndlessRecyclerOnScrollListener(footerAdapter) {
                override fun onLoadMore(currentPage: Int) {
                    footerAdapter.clear()
                    val progressItem = ProgressItem()
                    progressItem.isEnabled = false
                    footerAdapter.add(progressItem)
                    searchViewModel.onLoadMore(nextPage = currentPage + 1)
                }
            }
            addOnScrollListener(endlessScrollListener)
        }
        fastAdapter.addEventHook(object : ClickEventHook<ItemSearch>() {
            override fun onClick(
                v: View,
                position: Int,
                fastAdapter: FastAdapter<ItemSearch>,
                item: ItemSearch
            ) {
                when (v.id) {
                    R.id.item_delete_icon -> searchViewModel.deleteItem(item.entity.id)
                    R.id.item_card -> openItemInBrowser(item.entity.html_url)
                }
            }

            override fun onBindMany(viewHolder: RecyclerView.ViewHolder): List<View>? {
                return if (viewHolder is ItemSearch.SearchItemViewHolder) {
                    listOf(viewHolder.deleteIcon, viewHolder.card)
                } else {
                    emptyList()
                }
            }
        })
    }

    private fun openItemInBrowser(url: String) {
        requireContext().launchBrowser(url)
    }

    private fun listeners() {
        searchInput()
    }

    private fun searchInput() {
        searchBtn.setOnClickListener {
            hideSoftKeyboard()
            val query = searchInputEditText.text.toString()
            searchViewModel.search(query)
            resetEndlessScrollPageCount()
        }
    }

    private fun subscribeUi() {
        searchViewModel
            .searchLiveData
            .observe(viewLifecycleOwner, Observer(::processLiveDataResult))
    }

    private fun processLiveDataResult(liveDataResult: LiveDataResult<List<SearchItemEntity>>) {
        when (liveDataResult) {
            is Loading -> showProgressBar()
            is Success -> {
                val itemsEntity: List<SearchItemEntity>? = liveDataResult.data
                bindSearchItems(itemsEntity)
            }
            is Error -> {
                showErrorToast()
            }
            is Cache -> {
                footerAdapter.clear()
            }
        }
    }

    private fun resetEndlessScrollPageCount() {
        if (::endlessScrollListener.isInitialized) {
            endlessScrollListener.resetPageCount()
        }
    }

    private fun bindSearchItems(itemsEntity: List<SearchItemEntity>?) {
        footerAdapter.clear()
        if (itemsEntity.isNullOrEmpty()) {
            showPlaceholder()
        } else {
            showRecyclerView()
            val itemsSearch = ArrayList<ItemSearch>()
            itemsEntity.forEach { entity ->
                itemsSearch.add(ItemSearch(entity))
            }
            FastAdapterDiffUtil[itemAdapter] = itemsSearch
        }
    }

    private fun showRecyclerView() {
        searchResultList.visibility = View.VISIBLE
        loadingView.visibility = View.GONE
        itemsPlaceholder.visibility = View.GONE
    }

    private fun showProgressBar() {
        searchResultList.visibility = View.GONE
        itemsPlaceholder.visibility = View.GONE
        loadingView.visibility = View.VISIBLE
    }

    private fun showErrorToast() {
        loadingView.visibility = View.GONE
        Toast.makeText(context, R.string.error_toast, Toast.LENGTH_LONG).show()
    }

    private fun showPlaceholder() {
        itemsPlaceholder.visibility = View.VISIBLE
        loadingView.visibility = View.GONE
        searchResultList.visibility = View.GONE
    }
}
