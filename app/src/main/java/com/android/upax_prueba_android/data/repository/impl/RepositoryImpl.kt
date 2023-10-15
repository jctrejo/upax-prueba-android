package com.android.upax_prueba_android.data.repository.impl

import com.android.upax_prueba_android.core.AppResource
import com.android.upax_prueba_android.data.model.Pokemon
import com.android.upax_prueba_android.data.model.PokemonList
import com.android.upax_prueba_android.data.model.PokemonsDetailResponse
import com.android.upax_prueba_android.data.model.toPokemonEntity
import com.android.upax_prueba_android.data.repository.Repository
import com.android.upax_prueba_android.data.source.local.room.LocalPokemonDataSource
import com.android.upax_prueba_android.data.source.local.room.PokemonDatabase
import com.android.upax_prueba_android.data.source.remote.service.Service
import com.android.upax_prueba_android.ui.main.App
import com.android.upax_prueba_android.util.extension.internetCheck
import com.android.upax_prueba_android.util.extension.urlPokemon
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

open class RepositoryImpl @Inject constructor(
    private val api: Service,
) : Repository {

    override suspend fun fetchPokemon(limit: Int): Flow<AppResource<PokemonList>> =
        flow {
            val dataSourceLocal = App.getContext()
                ?.let { PokemonDatabase.getDatabase(it).pokemonDao() }
                ?.let { LocalPokemonDataSource(it) }

            if (App.getContext()?.internetCheck() == true) {
                try {
                    emit(AppResource.Loading)
                    val response = api.getPokemon(limit)

                    /*response.results.forEach {
                        val list = Pokemon(
                            id = it.url.getPokemonIDOfUrl(),
                            name = it.name,
                            url = it.id.toString().urlPokemon(),
                            base_experience = it.base_experience,
                            height = it.height,
                            is_default = it.is_default,
                            order = it.order,
                            weight = it.weight,
                            favorite = it.favorite
                        )
                        dataSourceLocal?.savePokemon(list.toPokemonEntity())
                    }*/

                    emit(AppResource.Success(response))

                    /*dataSourceLocal?.let { AppResource.Success(it.getLocalPokemons()) }
                        ?.let { emit(it) }*/

                } catch (exception: HttpException) {
                    emit(AppResource.Error(exception.message() ?: "no internet connection."))
                } catch (exception: IOException) {
                    emit(AppResource.Error(exception.toString()))
                }
            } else {
                dataSourceLocal?.let { AppResource.Success(it.getLocalPokemons()) }
                    ?.let { emit(it) }
            }
        }

    override suspend fun fetchDetailPokemon(pokemonID: Int): Flow<AppResource<PokemonsDetailResponse>> =
        flow {
            val dataSourceLocal = App.getContext()
                ?.let { PokemonDatabase.getDatabase(it).pokemonDao() }
                ?.let { LocalPokemonDataSource(it) }
            try {
                emit(AppResource.Loading)
                val response = api.getDetailPokemon(pokemonID)

                val list = Pokemon(
                    id = response.id,
                    name = response.name,
                    url = response.id.toString().urlPokemon(),
                    base_experience = response.base_experience,
                    height = response.height,
                    is_default = response.is_default,
                    order = response.order,
                    weight = response.weight,
                    favorite = false
                )
                dataSourceLocal?.savePokemon(list.toPokemonEntity())

                emit(AppResource.Success(response))
            } catch (exception: HttpException) {
                emit(AppResource.Error(exception.message() ?: "no internet connection."))
            } catch (exception: IOException) {
                emit(AppResource.Error(exception.toString()))
            }
        }

    override suspend fun fetchLocalPokemon(): Flow<AppResource<PokemonList>> =
        flow {
            val dataSourceLocal = App.getContext()
                ?.let { PokemonDatabase.getDatabase(it).pokemonDao() }
                ?.let { LocalPokemonDataSource(it) }
            try {
                emit(AppResource.Loading)
                dataSourceLocal?.let { AppResource.Success(it.getLocalPokemons()) }
                    ?.let { emit(it) }
            } catch (exception: HttpException) {
                emit(AppResource.Error(exception.message() ?: "no internet connection."))
            } catch (exception: IOException) {
                emit(AppResource.Error(exception.toString()))
            }
        }

    override suspend fun saveLocalPokemon(response: Pokemon): Flow<AppResource<Pokemon>> =
        flow {
            val dataSourceLocal = App.getContext()
                ?.let { PokemonDatabase.getDatabase(it).pokemonDao() }
                ?.let { LocalPokemonDataSource(it) }
            try {
                emit(AppResource.Loading)
                val pokemon = Pokemon(
                    id = response.id,
                    name = response.name,
                    url = response.id.toString().urlPokemon(),
                    base_experience = response.base_experience,
                    height = response.height,
                    is_default = response.is_default,
                    order = response.order,
                    weight = response.weight,
                    favorite = response.favorite
                )
                dataSourceLocal?.savePokemon(pokemon.toPokemonEntity())
                emit(AppResource.Success(pokemon))
            } catch (exception: HttpException) {
                emit(AppResource.Error(exception.message() ?: "no internet connection."))
            } catch (exception: IOException) {
                emit(AppResource.Error(exception.toString()))
            }
        }
}
