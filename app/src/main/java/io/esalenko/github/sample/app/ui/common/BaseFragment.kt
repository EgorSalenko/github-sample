package io.esalenko.github.sample.app.ui.common

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment


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

}