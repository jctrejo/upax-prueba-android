package com.android.upax_prueba_android.domain.usecase.pokemon

import com.android.upax_prueba_android.core.AppResource
import com.android.upax_prueba_android.data.model.PokemonsDetailResponse
import kotlinx.coroutines.flow.Flow

interface PokemonDetailUseCase {
    suspend operator fun invoke(pokemonID: Int): Flow<AppResource<PokemonsDetailResponse>>
}
