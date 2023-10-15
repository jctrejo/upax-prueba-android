package com.android.upax_prueba_android.domain.usecase.pokemon.impl

import com.android.upax_prueba_android.domain.usecase.pokemon.PokemonDetailUseCase
import com.android.upax_prueba_android.core.AppResource
import com.android.upax_prueba_android.data.model.PokemonsDetailResponse
import com.android.upax_prueba_android.data.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PokemonDetailUseCaseImpl @Inject constructor(private val repository: Repository) :
    PokemonDetailUseCase {

    override suspend operator fun invoke(pokemonID: Int): Flow<AppResource<PokemonsDetailResponse>> =
        repository.fetchDetailPokemon(pokemonID)
}
