package io.esalenko.github.sample.app.helper

import android.view.ViewManager
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textview.MaterialTextView
import io.esalenko.github.sample.app.R
import org.jetbrains.anko.custom.ankoView


inline fun ViewManager.materialTextView(
    init: MaterialTextView.() -> Unit = {}
): MaterialTextView = ankoView({ MaterialTextView(it) }, theme = 0, init = init)

inline fun ViewManager.materialCardView(
    init: MaterialCardView.() -> Unit = {}
): MaterialCardView = ankoView({ MaterialCardView(it) }, theme = 0, init = init)

fun ImageView.setCircleImage(url: String) {
    Glide
        .with(this)
        .load(url)
        .apply(RequestOptions.circleCropTransform())
        .into(this)
}

fun ImageView.setRoundedCornersImage(url: String) {
    val radius = this.context.resources.getDimension(R.dimen.margin_small_xxx).toInt()
    Glide
        .with(this)
        .load(url)
        .transform(RoundedCorners(radius))
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)
}
