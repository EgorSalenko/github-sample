package io.esalenko.github.sample.app.ui.splash

import android.os.Bundle
import androidx.lifecycle.Observer
import io.esalenko.github.sample.app.R
import io.esalenko.github.sample.app.ui.common.BaseActivity
import io.esalenko.github.sample.app.ui.common.MainActivity
import io.esalenko.github.sample.app.ui.login.LoginActivity
import org.jetbrains.anko.startActivity
import org.koin.android.viewmodel.ext.android.viewModel


class SplashActivity : BaseActivity(R.layout.activity_splash) {

    private val splashViewModel by viewModel<SplashViewModel>()

    override fun onReady(savedInstanceState: Bundle?) {
        super.onReady(savedInstanceState)
        subscribeUi()
        splashViewModel.validateToken()
    }

    private fun subscribeUi() {
        splashViewModel.tokenLiveData.observe(this, Observer { event ->
            when (event.getContentIfNotHandled()) {
                is SplashViewModel.TokenResult.Valid -> openSearchScreen()
                is SplashViewModel.TokenResult.Empty,
                SplashViewModel.TokenResult.Invalid -> openLoginScreen()
            }
        })
    }

    private fun openLoginScreen() {
        startActivity<LoginActivity>()
        finish()
    }

    private fun openSearchScreen() {
        startActivity<MainActivity>()
        finish()
    }
}
