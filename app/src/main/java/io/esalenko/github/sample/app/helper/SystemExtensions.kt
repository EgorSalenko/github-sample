package io.esalenko.github.sample.app.helper

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import timber.log.Timber


fun Context.launchBrowser(url: String) {
    try {
        CustomTabsIntent.Builder()
            .build()
            .launchUrl(
                this,
                Uri.parse(url)
            )
    } catch (e: Exception) {
        Timber.e(e)
        startActivity(Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(url)
        })
    }
}
