package io.esalenko.github.sample.app.helper

import android.view.ViewManager
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textview.MaterialTextView
import org.jetbrains.anko.custom.ankoView


inline fun ViewManager.materialTextView(
    init: MaterialTextView.() -> Unit = {}
): MaterialTextView = ankoView({ MaterialTextView(it) }, theme = 0, init = init)

inline fun ViewManager.materialCardView(
    init: MaterialCardView.() -> Unit = {}
): MaterialCardView = ankoView({ MaterialCardView(it) }, theme = 0, init = init)

