package io.esalenko.github.sample.app.ui.common

import android.os.Bundle
import androidx.lifecycle.Observer
import io.esalenko.github.sample.app.R
import io.esalenko.github.sample.app.helper.setCircleImage
import io.esalenko.github.sample.app.ui.search.SearchFragment
import io.esalenko.github.sample.app.ui.user.ProfileActivity
import io.esalenko.github.sample.app.ui.user.UserViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity
import org.koin.android.viewmodel.ext.android.viewModel


class MainActivity : BaseActivity(R.layout.activity_main) {

    private val userViewModel by viewModel<UserViewModel>()

    override fun onReady(savedInstanceState: Bundle?) {
        super.onReady(savedInstanceState)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(
                    R.id.fragment_container,
                    SearchFragment.newInstance(),
                    SearchFragment.TAG
                )
                .commitAllowingStateLoss()
        }
        setSupportActionBar(main_toolbar)
        listeners()
    }

    private fun listeners() {
        onUserIconClickListener()
    }

    private fun onUserIconClickListener() {
        user_avatar.setOnClickListener {
            openUserProfile()
        }
    }

    private fun openUserProfile() {
        startActivity<ProfileActivity>()
    }

    override fun onInitView(savedInstanceState: Bundle?) {
        super.onInitView(savedInstanceState)
        subscribeUi()
    }

    private fun subscribeUi() {
        userViewModel
            .profileLiveData
            .observe(this, Observer { url ->
                user_avatar.setCircleImage(url)
        })
    }
}
