package io.esalenko.github.sample.app.helper

import android.net.Uri
import io.esalenko.github.sample.app.BuildConfig
import io.esalenko.github.sample.app.data.Constants
import net.openid.appauth.*
import net.openid.appauth.browser.*


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

    fun onCreatedClientSecretBasic() = ClientSecretBasic(BuildConfig.CLIENT_SECRET)

    fun onCreatedAppAuthConfig() = AppAuthConfiguration.Builder()
        .setBrowserMatcher(
            BrowserWhitelist(
                VersionedBrowserMatcher.CHROME_CUSTOM_TAB,
                VersionedBrowserMatcher.SAMSUNG_CUSTOM_TAB
            )
        )
        // prevent the use of a buggy version of the custom tabs in Samsung SBrowser:
        .setBrowserMatcher(
            BrowserBlacklist(
                VersionedBrowserMatcher(
                    Browsers.SBrowser.PACKAGE_NAME,
                    Browsers.SBrowser.SIGNATURE_SET,
                    true, // when this browser is used via a custom tab
                    VersionRange.atMost("5.3")
                )
            )
        )
        .build()
}
