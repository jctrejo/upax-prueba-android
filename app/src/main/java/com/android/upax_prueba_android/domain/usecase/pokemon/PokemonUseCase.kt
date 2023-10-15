package com.android.upax_prueba_android.domain.usecase.pokemon

import com.android.upax_prueba_android.core.AppResource
import com.android.upax_prueba_android.data.model.PokemonList
import kotlinx.coroutines.flow.Flow

interface PokemonUseCase {
    suspend operator fun invoke(limit: Int): Flow<AppResource<PokemonList>>
}
