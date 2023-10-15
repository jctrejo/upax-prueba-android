package com.android.upax_prueba_android.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

data class PokemonList(val results: List<Pokemon> = listOf())

data class Pokemon(
    val id: Int = 0,
    val name: String = "",
    val url: String = "",
    val base_experience: Int = 0,
    val height: Int = 0,
    val is_default: Boolean = false,
    val order: Int = 0,
    val weight: Int = 0,
    val favorite: Boolean = false,
)

@Entity
data class PokemonsListEntity(
    @PrimaryKey
    val id: Int = -1,
    @ColumnInfo(name = "name")
    val name: String = "",
    @ColumnInfo(name = "url")
    val url: String = "",
    @ColumnInfo(name = "base_experience")
    val base_experience: Int = 0,
    @ColumnInfo(name = "height")
    val height: Int = 0,
    @ColumnInfo(name = "is_default")
    val is_default: Boolean = false,
    @ColumnInfo(name = "order")
    val order: Int = 0,
    @ColumnInfo(name = "weight")
    val weight: Int = 0,
    @ColumnInfo(name = "favorite")
    val favorite: Boolean = false,
)

fun List<PokemonsListEntity>.toPokemonList(): PokemonList {
    val resultList = mutableListOf<Pokemon>()
    this.forEach { pokemonEntity ->
        resultList.add(pokemonEntity.toPokemon())
    }
    return PokemonList(resultList)
}

fun PokemonsListEntity.toPokemon(): Pokemon = Pokemon(
    this.id,
    this.name,
    this.url,
    this.base_experience,
    this.height,
    this.is_default,
    this.order,
    this.weight,
    this.favorite
)

fun Pokemon.toPokemonEntity(): PokemonsListEntity = PokemonsListEntity(
    this.id,
    this.name,
    this.url,
    this.base_experience,
    this.height,
    this.is_default,
    this.order,
    this.weight,
    this.favorite
)