package io.esalenko.github.sample.app.ui.search.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import com.google.android.material.textview.MaterialTextView
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.items.AbstractItem
import io.esalenko.github.sample.app.R
import io.esalenko.github.sample.app.data.db.entity.SearchItemEntity
import io.esalenko.github.sample.app.helper.withSpellingSuffix
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find

class ItemSearch(val entity: SearchItemEntity) :
    AbstractItem<ItemSearch.SearchItemViewHolder>() {

    override fun createView(ctx: Context, parent: ViewGroup?): View {
        return ItemSearchUi
            .newInstance()
            .createView(AnkoContext.createReusable(ctx, this))
    }

    override val layoutRes: Int
        get() = 0
    override val type: Int
        get() = R.id.fastadapter_search_item

    override fun getViewHolder(v: View) = SearchItemViewHolder(v)

    init {
        identifier = entity.stargazers_count.toLong()
    }

    class SearchItemViewHolder(view: View) : FastAdapter.ViewHolder<ItemSearch>(view) {

        var card = view.find<CardView>(R.id.item_card)
        private var fullNameTextView = view.find<MaterialTextView>(R.id.item_full_name)
        private var descriptionTextView = view.find<MaterialTextView>(R.id.item_description)
        private var starsTextView = view.find<MaterialTextView>(R.id.item_stars)
        private var languageTextView = view.find<MaterialTextView>(R.id.item_language)
        var deleteIcon = view.find<ImageView>(R.id.item_delete_icon)

        override fun bindView(item: ItemSearch, payloads: MutableList<Any>) {
            with(item.entity) {
                fullNameTextView.text = full_name
                starsTextView.text = stargazers_count.withSpellingSuffix()
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
            // not applicable
        }
    }
}
