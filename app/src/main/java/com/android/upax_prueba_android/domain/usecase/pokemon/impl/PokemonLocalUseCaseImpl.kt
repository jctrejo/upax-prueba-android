package com.android.upax_prueba_android.domain.usecase.pokemon.impl

import com.android.upax_prueba_android.domain.usecase.pokemon.PokemonLocalUseCase
import com.android.upax_prueba_android.core.AppResource
import com.android.upax_prueba_android.data.model.PokemonList
import com.android.upax_prueba_android.data.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PokemonLocalUseCaseImpl @Inject constructor(private val repository: Repository) :
    PokemonLocalUseCase {

    override suspend operator fun invoke(): Flow<AppResource<PokemonList>> =
        repository.fetchLocalPokemon()
}
