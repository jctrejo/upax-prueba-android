package com.android.upax_prueba_android.domain.usecase.pokemon.impl

import com.android.upax_prueba_android.domain.usecase.pokemon.SavePokemonUseCase
import com.android.upax_prueba_android.core.AppResource
import com.android.upax_prueba_android.data.model.Pokemon
import com.android.upax_prueba_android.data.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SavePokemonUseCaseImpl @Inject constructor(private val repository: Repository) :
    SavePokemonUseCase {

    override suspend operator fun invoke(
        pokemon: Pokemon,
    ): Flow<AppResource<Pokemon>> =
        repository.saveLocalPokemon(pokemon)
}
