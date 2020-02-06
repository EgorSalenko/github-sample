package io.esalenko.github.sample.app.ui.common

import android.os.Bundle
import io.esalenko.github.sample.app.R
import io.esalenko.github.sample.app.ui.search.SearchFragment


class MainActivity : BaseActivity(R.layout.activity_main) {

    override fun onReady(savedInstanceState: Bundle?) {
        super.onReady(savedInstanceState)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, SearchFragment.newInstance(), SearchFragment.TAG)
                .commitAllowingStateLoss()
        }
    }
}
