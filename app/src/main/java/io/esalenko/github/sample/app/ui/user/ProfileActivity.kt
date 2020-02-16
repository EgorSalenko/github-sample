package io.esalenko.github.sample.app.ui.user

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import io.esalenko.github.sample.app.R
import io.esalenko.github.sample.app.data.model.user.User
import io.esalenko.github.sample.app.helper.launchBrowser
import io.esalenko.github.sample.app.helper.setRoundedCornersImage
import io.esalenko.github.sample.app.ui.common.BaseActivity
import io.esalenko.github.sample.app.ui.common.LiveDataResult
import kotlinx.android.synthetic.main.activity_profile.*
import org.koin.android.viewmodel.ext.android.viewModel


class ProfileActivity : BaseActivity(R.layout.activity_profile) {

    private val userViewModel by viewModel<UserViewModel>()

    private lateinit var userUrl: String

    override fun onReady(savedInstanceState: Bundle?) {
        super.onReady(savedInstanceState)
        subscribeUi()
    }

    override fun onInitView(savedInstanceState: Bundle?) {
        super.onInitView(savedInstanceState)
        setSupportActionBar(toolbar_profile)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        listeners()
    }

    private fun listeners() {
        onUserProfilePhotoClickListener()
    }

    private fun onUserProfilePhotoClickListener() {
        user_avatar.setOnClickListener {
            if (::userUrl.isInitialized) {
                launchBrowser(userUrl)
            }
        }
    }

    private fun subscribeUi() {
        userViewModel.userLiveData.observe(this, Observer { result ->
            when (result) {
                is LiveDataResult.Success -> {
                    val userData = result.data ?: return@Observer
                    bindUserData(userData)
                }
                is LiveDataResult.Error -> {
                    Toast.makeText(this, result.msg, Toast.LENGTH_SHORT).show()
                }
                is LiveDataResult.Loading -> {
                }
                is LiveDataResult.Cache -> {
                }
            }
        })
    }

    private fun bindUserData(user: User) {
        with(user) {
            userUrl = html_url
            user_avatar.setRoundedCornersImage(avatar_url)
            user_name.text = name
            user_login.text = login
            user_bio.text = bio
            user_location.text = location
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
