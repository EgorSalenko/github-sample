package io.esalenko.github.sample.app.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import io.esalenko.github.sample.app.R
import io.esalenko.github.sample.app.ui.common.BaseActivity
import io.esalenko.github.sample.app.ui.common.MainActivity
import io.esalenko.github.sample.app.ui.login.LoginActivity
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
            // TODO :: check token validity
            // https://developer.github.com/v3/apps/oauth_applications/
            when (event.getContentIfNotHandled()) {
                is SplashViewModel.TokenResult.Valid -> openSearchScreen()
                is SplashViewModel.TokenResult.Empty, SplashViewModel.TokenResult
                    .Invalid -> openLoginScreen()
            }
        })
    }

    private fun openLoginScreen() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun openSearchScreen() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
