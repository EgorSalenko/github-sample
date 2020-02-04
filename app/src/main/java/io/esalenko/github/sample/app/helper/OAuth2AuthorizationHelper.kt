package io.esalenko.github.sample.app.helper

import android.net.Uri
import io.esalenko.github.sample.app.BuildConfig
import io.esalenko.github.sample.app.data.Constants
import net.openid.appauth.AuthorizationRequest
import net.openid.appauth.AuthorizationServiceConfiguration
import net.openid.appauth.ResponseTypeValues


class OAuth2AuthorizationHelper {

    fun onCreateAuthRequest(): AuthorizationRequest {
        val serviceConfiguration = AuthorizationServiceConfiguration(
            Uri.parse(Constants.AUTH_URL),
            Uri.parse(Constants.TOKEN_URL)
        )

        val redirectUri = Uri.parse("io.esalenko://callback")

        return AuthorizationRequest.Builder(
                serviceConfiguration,
                BuildConfig.CLIENT_ID,
                ResponseTypeValues.CODE,
                redirectUri
            )
            .setScopes("repo")
            .build()
    }
}
