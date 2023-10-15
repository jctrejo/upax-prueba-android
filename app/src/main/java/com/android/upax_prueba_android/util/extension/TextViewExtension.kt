package com.android.upax_prueba_android.util.extension

import android.content.Context
import android.widget.TextView

fun TextView.colorText(color: Int, context: Context) {
    return this.setTextColor(this.resources.getColor(color))
}