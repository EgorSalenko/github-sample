package io.esalenko.github.sample.app.ui.common

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.Fragment
import timber.log.Timber


abstract class BaseFragment(@LayoutRes layoutRes: Int) : Fragment(layoutRes), BaseAACView {

    override fun onActivityCreated(
        savedInstanceState: Bundle?
    ) {
        super.onActivityCreated(savedInstanceState)
        onPrepare(savedInstanceState)
        onInitView(savedInstanceState)
        onReady(savedInstanceState)
    }

    override fun onPrepare(savedInstanceState: Bundle?) {}

    override fun onReady(savedInstanceState: Bundle?) {}

    override fun onInitView(savedInstanceState: Bundle?) {}

    fun hideSoftKeyboard() {
        val inputMethodManager = activity?.getSystemService(
            Activity.INPUT_METHOD_SERVICE
        ) as InputMethodManager?
        inputMethodManager?.hideSoftInputFromWindow(
            activity?.currentFocus?.windowToken, 0
        )
    }

    fun launchBrowser(url: String) {
        try {
            CustomTabsIntent.Builder()
                .build()
                .launchUrl(
                    context!!,
                    Uri.parse(url)
                )
        } catch (e: Exception) {
            Timber.e(e)
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(url)
            })
        }
    }
}
