package io.esalenko.github.sample.app.ui.common

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity(@LayoutRes layoutResId: Int) :
    AppCompatActivity(layoutResId),
    BaseAACView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onPrepare(savedInstanceState)
        onInitView(savedInstanceState)
        onReady(savedInstanceState)
    }

    override fun onPrepare(savedInstanceState: Bundle?) {}

    override fun onReady(savedInstanceState: Bundle?) {}

    override fun onInitView(savedInstanceState: Bundle?) {}
}
