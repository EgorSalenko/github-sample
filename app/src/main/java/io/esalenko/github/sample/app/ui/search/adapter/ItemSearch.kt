package io.esalenko.github.sample.app.ui.search.adapter

import android.view.View
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.items.AbstractItem
import io.esalenko.github.sample.app.R
import io.esalenko.github.sample.app.data.db.entity.SearchItemEntity
import kotlinx.android.synthetic.main.layout_item.view.*


class ItemSearch(private val entity: SearchItemEntity) :
    AbstractItem<ItemSearch.SearchItemViewHolder>() {
    override val layoutRes: Int
        get() = R.layout.layout_item
    override val type: Int
        get() = R.id.fastadapter_search_item

    override fun getViewHolder(v: View) = SearchItemViewHolder(v)


    class SearchItemViewHolder(view: View) : FastAdapter.ViewHolder<ItemSearch>(view) {

        private var fullNameTextView = view.itemFullName
        private var descriptionTextView = view.itemDescription
        private var starsTextView = view.itemStars
        private var languageTextView = view.itemLang

        override fun bindView(item: ItemSearch, payloads: MutableList<Any>) {
            with(item.entity) {
                fullNameTextView.text = full_name
                starsTextView.text = stargazers_count.toString()
                if (description == null) {
                    descriptionTextView.visibility = View.GONE
                } else {
                    descriptionTextView.text = description
                }
                if (language == null) {
                    languageTextView.visibility = View.GONE
                } else {
                    languageTextView.text = language
                }
            }
        }

        override fun unbindView(item: ItemSearch) {

        }
    }
}