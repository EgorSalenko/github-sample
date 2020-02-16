package io.esalenko.github.sample.app.ui.common

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import io.esalenko.github.sample.app.R
import io.esalenko.github.sample.app.ui.search.SearchFragment
import io.esalenko.github.sample.app.ui.user.UserViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel


class MainActivity : BaseActivity(R.layout.activity_main) {

    private val userViewModel by viewModel<UserViewModel>()

    override fun onReady(savedInstanceState: Bundle?) {
        super.onReady(savedInstanceState)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, SearchFragment.newInstance(), SearchFragment.TAG)
                .commitAllowingStateLoss()
        }
        setSupportActionBar(main_toolbar)
    }

    override fun onInitView(savedInstanceState: Bundle?) {
        super.onInitView(savedInstanceState)
        subscribeUi()
    }

    private fun subscribeUi() {
        userViewModel.userLiveData.observe(this, Observer { result ->
            when (result) {
                is LiveDataResult.Success -> {
                    val userData = result.data ?: return@Observer
                    setUserAvatar(userData.avatar_url)
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

    private fun setUserAvatar(url: String) {
        Glide
            .with(this)
            .load(url)
            .apply(RequestOptions.circleCropTransform())
            .into(user_avatar)
    }
}
