package io.esalenko.github.sample.app.ui.search

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.mikepenz.fastadapter.diff.FastAdapterDiffUtil
import com.mikepenz.fastadapter.scroll.EndlessRecyclerOnScrollListener
import io.esalenko.github.sample.app.R
import io.esalenko.github.sample.app.data.db.entity.SearchItemEntity
import io.esalenko.github.sample.app.ui.common.BaseFragment
import io.esalenko.github.sample.app.ui.common.LiveDataResult
import io.esalenko.github.sample.app.ui.common.LiveDataResult.*
import io.esalenko.github.sample.app.ui.search.adapter.ItemSearch
import kotlinx.android.synthetic.main.fragment_search.*
import org.koin.android.viewmodel.ext.android.viewModel


class SearchFragment : BaseFragment(R.layout.fragment_search) {

    companion object {
        const val TAG = "SearchFragment"
        fun newInstance() = SearchFragment()
    }

    private val searchViewModel by viewModel<SearchViewModel>()

    private lateinit var fastAdapter: FastAdapter<ItemSearch>
    private lateinit var itemAdapter: ItemAdapter<ItemSearch>

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
        fastAdapter = FastAdapter.with(itemAdapter)
        val endlessScrollListener = object : EndlessRecyclerOnScrollListener() {
            override fun onLoadMore(currentPage: Int) {
                searchViewModel.onLoadMore(currentPage)
            }

        }

        searchResultList.adapter = fastAdapter
        searchResultList.layoutManager = LinearLayoutManager(context)
        searchResultList.itemAnimator = DefaultItemAnimator()
        searchResultList.addOnScrollListener(endlessScrollListener)
    }

    private fun listeners() {
        searchInput()
    }

    private fun searchInput() {
        searchBtn.setOnClickListener {
            val query = searchInputEditText.text.toString()
            searchViewModel.search(query)
        }
    }

    private fun subscribeUi() {
        searchViewModel.searchLiveData
            .observe(viewLifecycleOwner, Observer(::processLiveDataResult))
    }

    private fun processLiveDataResult(liveDataResult: LiveDataResult<List<SearchItemEntity>>) {
        when (liveDataResult) {
            is Loading -> showProgressBar()
            is Success -> {
                val itemsEntity: List<SearchItemEntity>? = liveDataResult.data
                bindSearchItems(itemsEntity)
            }
            is Error -> showErrorToast()
        }
    }

    private fun bindSearchItems(itemsEntity: List<SearchItemEntity>?) {
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
        itemsPlaceholder.visibility = View.VISIBLE
        Toast.makeText(context, R.string.error_toast, Toast.LENGTH_LONG).show()
    }

    private fun showPlaceholder() {
        itemsPlaceholder.visibility = View.VISIBLE
        loadingView.visibility = View.GONE
        searchResultList.visibility = View.GONE
    }

}
