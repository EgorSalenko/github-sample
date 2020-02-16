package io.esalenko.github.sample.app.ui.search.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.mikepenz.fastadapter.items.AbstractItem
import io.esalenko.github.sample.app.R


class ProgressItem : AbstractItem<ProgressItem.FooterItemViewHolder>() {

    override val layoutRes: Int
        get() = R.layout.item_progress

    override val type: Int
        get() = R.id.fastadapter_footer_item

    override fun getViewHolder(v: View) = FooterItemViewHolder(v)

    class FooterItemViewHolder(view: View) : RecyclerView.ViewHolder(view)
}
