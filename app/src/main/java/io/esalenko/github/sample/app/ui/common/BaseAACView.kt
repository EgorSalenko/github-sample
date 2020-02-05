package io.esalenko.github.sample.app.ui.common


interface BaseAACView {
    fun onInitView(savedInstanceState: android.os.Bundle?)
    fun onPrepare(savedInstanceState: android.os.Bundle?)
    fun onReady(savedInstanceState: android.os.Bundle?)
}
