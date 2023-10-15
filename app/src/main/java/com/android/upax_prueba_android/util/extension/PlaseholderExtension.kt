package com.android.upax_prueba_android.util.extension

import android.content.Context
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.android.upax_prueba_android.R
import com.android.upax_prueba_android.data.model.ProfileModel

fun ImageView.validateImageProfile(
    data: ProfileModel,
    constraintLayout: ConstraintLayout,
    text: TextView,
    colorContainer: LinearLayout?,
    placeholderContainer: LinearLayout?,
    context: Context
) {
    val name = data.name

    if (data.url.isEmpty() && name.isEmpty()) {
        this.glidePlaceholder(R.drawable.ic_avatar, context, onAccept = {}, onError = {})
        this.show()
        constraintLayout.hide()
        colorContainer?.hide()
        placeholderContainer?.show()
    } else {
        if (data.url.isEmpty()) {
            val letterSeparation = name.letterSeparation()
            val firstLetter = letterSeparation[0].substring(0, 1)
            if (firstLetter.isLetters()) {
                showImageProfile(letterSeparation, this, constraintLayout, text)
                colorContainer?.show()
                placeholderContainer?.hide()
            } else {
                hideImageProfile(this, constraintLayout, context)
                colorContainer?.hide()
                placeholderContainer?.show()
            }
        } else {
           this.glidePlaceholder(data.url, context, onAccept = {
               constraintLayout.hide()
           }, onError = {
               val letterSeparation = name.letterSeparation()
               val firstLetter = letterSeparation[0].substring(0, 1)
               if (firstLetter.isLetters()) {
                   showImageProfile(letterSeparation, this, constraintLayout, text)
                   colorContainer?.show()
               }
           })
        }
    }
}

private fun showImageProfile(
    letterSeparation: List<String>,
    imageView: ImageView,
    constraintLayout: ConstraintLayout,
    text: TextView,
) {
    text.text =
        if (letterSeparation.size > 1)
            letterSeparation.toInitialsLetters()
        else
            letterSeparation.toFirstInitialsLetters()
    constraintLayout.show()
    imageView.invisible()
}

private  fun hideImageProfile(imageView: ImageView, constraintLayout: ConstraintLayout, context: Context) {
    imageView.glidePlaceholder(R.drawable.ic_avatar, context, onAccept = {}, onError = {})
    constraintLayout.hide()
    imageView.show()
}