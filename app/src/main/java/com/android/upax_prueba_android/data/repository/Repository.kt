package com.android.upax_prueba_android.data.repository

import com.android.upax_prueba_android.core.AppResource
import com.android.upax_prueba_android.data.model.Pokemon
import com.android.upax_prueba_android.data.model.PokemonList
import com.android.upax_prueba_android.data.model.PokemonsDetailResponse
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun fetchPokemon(limit: Int): Flow<AppResource<PokemonList>>

    suspend fun fetchDetailPokemon(pokemonID: Int): Flow<AppResource<PokemonsDetailResponse>>

    suspend fun fetchLocalPokemon(): Flow<AppResource<PokemonList>>

    suspend fun saveLocalPokemon(pokemon: Pokemon): Flow<AppResource<Pokemon>>

}