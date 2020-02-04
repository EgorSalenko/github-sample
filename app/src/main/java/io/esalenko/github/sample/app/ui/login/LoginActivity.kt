package io.esalenko.github.sample.app.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import io.esalenko.github.sample.app.BuildConfig
import io.esalenko.github.sample.app.R
import io.esalenko.github.sample.app.helper.OAuth2AuthorizationHelper
import io.esalenko.github.sample.app.ui.common.BaseActivity
import kotlinx.android.synthetic.main.activity_login.*
import net.openid.appauth.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel


class LoginActivity : BaseActivity(R.layout.activity_login) {

    companion object {
        const val TAG = "LoginActivity"
        private const val USED_INTENT = "key_used_intent"
        private const val RC_AUTH = 9002
    }

    private val loginViewModel by viewModel<LoginViewModel>()
    private val authHelper by inject<OAuth2AuthorizationHelper>()


    override fun onStart() {
        super.onStart()
        checkIntent(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onClickListeners()
    }

    private fun onClickListeners() {
        signInButton.setOnClickListener {
            signIn()
        }
    }

    private fun signIn() {
        val authorizationRequest = authHelper.onCreateAuthRequest()
        val authorizationService = AuthorizationService(this)
        val authIntent = authorizationService.getAuthorizationRequestIntent(authorizationRequest)
        startActivityForResult(authIntent, RC_AUTH)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        checkIntent(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            RC_AUTH -> {
                data?.let {
                    checkIntent(it)
                }
            }
        }
    }

    private fun checkIntent(intent: Intent) {
        if (!intent.hasExtra(USED_INTENT)) {
            handleAuthorizationResponse(intent)
            intent.putExtra(USED_INTENT, true)
        }
    }

    private fun handleAuthorizationResponse(intent: Intent) {

        val response = AuthorizationResponse.fromIntent(intent) ?: return
        val error = AuthorizationException.fromIntent(intent)

        error?.printStackTrace()

        val authState = AuthState(response, error)
        val clientAuth = ClientSecretBasic(BuildConfig.CLIENT_SECRET)
        AuthorizationService(this).performTokenRequest(
            response.createTokenExchangeRequest(),
            clientAuth
        )
        { tokenResponse, exception ->
            exception?.let { e ->
                Log.w(TAG, "Token Exchange failed", e)
                return@performTokenRequest
            }
            tokenResponse?.let { response ->
                authState.update(response, exception)
                persistAuthState(authState)
                Log.i(
                    TAG,
                    String.format(
                        "Token Response [ Access Token: %s, ID Token: %s ]",
                        response.accessToken,
                        response.idToken
                    )
                )
            }
        }
    }

    private fun persistAuthState(authState: AuthState) {
        loginViewModel.persistAuthState(authState)
    }
}
