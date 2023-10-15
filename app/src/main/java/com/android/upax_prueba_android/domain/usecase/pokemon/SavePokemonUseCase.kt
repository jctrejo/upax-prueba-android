package com.android.upax_prueba_android.domain.usecase.pokemon

import com.android.upax_prueba_android.core.AppResource
import com.android.upax_prueba_android.data.model.Pokemon
import kotlinx.coroutines.flow.Flow

interface SavePokemonUseCase {
    suspend operator fun invoke(pokemon: Pokemon): Flow<AppResource<Pokemon>>
}
