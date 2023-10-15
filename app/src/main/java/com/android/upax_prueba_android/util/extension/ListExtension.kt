package com.android.upax_prueba_android.util.extension

fun List<String>.toInitialsLetters(): String {
    return "${this[0].substring(0, 1)}${this[1].substring(0, 1)}"
}

fun List<String>.toFirstInitialsLetters(): String {
    return this[0].substring(0, 1)
}
