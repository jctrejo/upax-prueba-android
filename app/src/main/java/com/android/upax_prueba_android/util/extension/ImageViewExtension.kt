package com.android.upax_prueba_android.util.extension

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.android.upax_prueba_android.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import javax.annotation.Nullable

fun ImageView.glidePlaceholder(
    uri: Any,
    context: Context?,
    onAccept: () -> Unit,
    onError: () -> Unit,
) {
    context?.let {
        Glide.with(it)
            .load(uri)
            .apply(
                RequestOptions()
                    .error(R.drawable.ic_avatar)
                    .centerCrop()
            )
            .listener(object : RequestListener<Drawable?> {
                override fun onLoadFailed(
                    @Nullable e: GlideException?,
                    model: Any?,
                    target: com.bumptech.glide.request.target.Target<Drawable?>?,
                    isFirstResource: Boolean,
                ): Boolean {
                    onError()
                    return true
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: com.bumptech.glide.request.target.Target<Drawable?>?,
                    dataSource: com.bumptech.glide.load.DataSource?,
                    isFirstResource: Boolean,
                ): Boolean {
                    onAccept()
                    return false
                }
            })
            .into(this)
    }
}

fun ImageView.colorImage(color: Int, context: Context) {
    this.setColorFilter(
        ContextCompat.getColor(context, color),
        android.graphics.PorterDuff.Mode.SRC_IN
    )
}