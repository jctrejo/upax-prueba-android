package com.android.upax_prueba_android.domain.usecase.pokemon.impl

import com.android.upax_prueba_android.domain.usecase.pokemon.PokemonUseCase
import com.android.upax_prueba_android.core.AppResource
import com.android.upax_prueba_android.data.model.PokemonList
import com.android.upax_prueba_android.data.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PokemonUseCaseImpl @Inject constructor(private val repository: Repository) :
    PokemonUseCase {

    override suspend operator fun invoke(limit: Int): Flow<AppResource<PokemonList>> =
        repository.fetchPokemon(limit)
}
