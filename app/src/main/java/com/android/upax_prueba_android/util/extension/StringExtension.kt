package com.android.upax_prueba_android.util.extension

import com.android.upax_prueba_android.core.Constants.URL_IMAGE
import java.util.Locale

fun String.letterSeparation(): List<String> {
    return this.split(" ").toList()
}

fun String.isLetters(): Boolean {
    return this.filter { it in 'A'..'Z' || it in 'a'..'z' }.length == this.length
}

fun String.urlPokemon(): String {
    return "$URL_IMAGE${this}.png"
}

fun String.getPokemonIDOfUrl() = this.substringAfter("pokemon").replace("/", "").toInt()

fun String.toCapitalLetter(): String? {
    return if (this.isEmpty()) {
        this
    } else {
        this.uppercase(Locale.getDefault())[0].toString() + this.substring(1, this.length)
            .lowercase(
                Locale.getDefault()
            )
    }
}