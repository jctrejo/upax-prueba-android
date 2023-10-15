package com.android.upax_prueba_android.data.di

import com.android.upax_prueba_android.data.source.remote.NetworkClient
import com.android.upax_prueba_android.data.source.remote.service.Service
import com.android.upax_prueba_android.data.repository.Repository
import com.android.upax_prueba_android.data.repository.impl.RepositoryImpl
import com.android.upax_prueba_android.domain.usecase.pokemon.PokemonDetailUseCase
import com.android.upax_prueba_android.domain.usecase.pokemon.PokemonLocalUseCase
import com.android.upax_prueba_android.domain.usecase.pokemon.PokemonUseCase
import com.android.upax_prueba_android.domain.usecase.pokemon.SavePokemonUseCase
import com.android.upax_prueba_android.domain.usecase.pokemon.impl.PokemonDetailUseCaseImpl
import com.android.upax_prueba_android.domain.usecase.pokemon.impl.PokemonLocalUseCaseImpl
import com.android.upax_prueba_android.domain.usecase.pokemon.impl.PokemonUseCaseImpl
import com.android.upax_prueba_android.domain.usecase.pokemon.impl.SavePokemonUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)

object Module {

    @Provides
    fun getServices(): Service {
        return NetworkClient().getService()
    }

    @Provides
    fun getRepository(service: Service): Repository {
        return RepositoryImpl(service)
    }

    @Provides
    fun getUseCasePokemon(repository: Repository): PokemonUseCase {
        return PokemonUseCaseImpl(repository)
    }

    @Provides
    fun getUseCasePokemonDetail(repository: Repository): PokemonDetailUseCase {
        return PokemonDetailUseCaseImpl(repository)
    }

    @Provides
    fun getUseCaseLocalPokemon(repository: Repository): PokemonLocalUseCase {
        return PokemonLocalUseCaseImpl(repository)
    }

    @Provides
    fun getUseCaseLocalPokemonFavorite(repository: Repository): SavePokemonUseCase {
        return SavePokemonUseCaseImpl(repository)
    }
}
