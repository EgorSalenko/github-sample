package io.esalenko.github.sample.app.ui.user.adapter

import android.view.View
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.items.AbstractItem
import io.esalenko.github.sample.app.R
import io.esalenko.github.sample.app.data.model.repos.UserRepo
import io.esalenko.github.sample.app.helper.withSpellingSuffix
import kotlinx.android.synthetic.main.item_github_repository.view.*


class UserReposItem(val userRepos: UserRepo) :
    AbstractItem<UserReposItem.UserRepoItemViewHolder>() {

    init {
        identifier = userRepos.id.toLong()
    }

    class UserRepoItemViewHolder(val view: View) : FastAdapter.ViewHolder<UserReposItem>(view) {
        var card = view.item_card_view
        private var fullNameTextView = view.itemFullName
        private var descriptionTextView = view.itemDescription
        private var starsTextView = view.itemStars
        private var languageTextView = view.itemLang

        override fun bindView(item: UserReposItem, payloads: MutableList<Any>) {
            with(item.userRepos) {
                fullNameTextView.text = name
                starsTextView.text = stargazers_count.withSpellingSuffix()
                descriptionTextView.text = if (description.isNullOrEmpty()) {
                    view.resources.getString(R.string.no_description_available)
                } else {
                    description
                }
                if (language.isNullOrEmpty()) {
                    languageTextView.visibility = View.GONE
                } else {
                    languageTextView.text = language
                }
            }
        }

        override fun unbindView(item: UserReposItem) {}
    }

    override val layoutRes: Int
        get() = R.layout.item_github_repository
    override val type: Int
        get() = R.id.fastadapter_user_repos_item

    override fun getViewHolder(v: View) = UserRepoItemViewHolder(v)
}
