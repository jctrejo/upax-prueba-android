package com.android.upax_prueba_android.data.source.remote.service

import com.android.upax_prueba_android.data.model.PokemonList
import com.android.upax_prueba_android.data.model.PokemonsDetailResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Service {

    @GET("/api/v2/pokemon")
    suspend fun getPokemon(
        @Query("limit") limit: Int
    ): PokemonList

    @GET("/api/v2/pokemon/{id}")
    suspend fun getDetailPokemon(
        @Path("id") id: Int
    ): PokemonsDetailResponse
}