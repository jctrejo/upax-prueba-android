package com.android.upax_prueba_android.data.source.local.room

import com.android.upax_prueba_android.data.model.PokemonList
import com.android.upax_prueba_android.data.model.PokemonsListEntity
import com.android.upax_prueba_android.data.model.toPokemonList

class LocalPokemonDataSource(
    private val pokemonDao: PokemonDao,
) {

    suspend fun getLocalPokemons(): PokemonList {
        return pokemonDao.getAllPokemons().toPokemonList()
    }

    suspend fun savePokemon(pokemon: PokemonsListEntity) {
        pokemonDao.savePokemon(pokemon)
    }
}
