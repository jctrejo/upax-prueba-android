package com.android.upax_prueba_android.ui.home

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.upax_prueba_android.core.AppResource
import com.android.upax_prueba_android.core.AppUiState
import com.android.upax_prueba_android.data.model.Pokemon
import com.android.upax_prueba_android.data.model.PokemonList
import com.android.upax_prueba_android.data.model.PokemonsDetailResponse
import com.android.upax_prueba_android.data.source.local.room.PokemonDetailEntity
import com.android.upax_prueba_android.data.source.local.room.PokemonRepository
import com.android.upax_prueba_android.domain.usecase.pokemon.PokemonDetailUseCase
import com.android.upax_prueba_android.domain.usecase.pokemon.PokemonLocalUseCase
import com.android.upax_prueba_android.domain.usecase.pokemon.PokemonUseCase
import com.android.upax_prueba_android.domain.usecase.pokemon.SavePokemonUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val pokemonUseCase: PokemonUseCase,
    private val pokemonDetailUseCase: PokemonDetailUseCase,
    private val pokemonLocalUseCase: PokemonLocalUseCase,
    private val savePokemonUseCase: SavePokemonUseCase,
    @ApplicationContext context: Context,
) : ViewModel() {

    private val repositoryLocal = PokemonRepository(context.applicationContext as Application)
    val pokemon = repositoryLocal.getPokemons()

    private val _uiStatePokemon = MutableLiveData<AppUiState<PokemonList>>()
    val uiStatePokemon: LiveData<AppUiState<PokemonList>> get() = _uiStatePokemon

    private val _uiStatePokemonDetail = MutableLiveData<AppUiState<PokemonsDetailResponse>>()
    val uiStatePokemonDetail: LiveData<AppUiState<PokemonsDetailResponse>> get() = _uiStatePokemonDetail

    private val _uiStateLocalPokemon = MutableLiveData<AppUiState<PokemonList>>()
    val uiStateLocalPokemon: LiveData<AppUiState<PokemonList>> get() = _uiStateLocalPokemon

    private val _uiStateLocalPokemonFavorite = MutableLiveData<AppUiState<Pokemon>>()
    val uiStateLocalPokemonFavorite: LiveData<AppUiState<Pokemon>> get() = _uiStateLocalPokemonFavorite

    fun getPokemon(limit: Int) = viewModelScope.launch {
        pokemonUseCase.invoke(limit).collect { result ->
            when(result) {
                is AppResource.Loading -> {
                    _uiStatePokemon.value = AppUiState.Loading()
                }

                is AppResource.Error -> {
                    _uiStatePokemon.value = AppUiState.Error(result.throwable)
                }

                is AppResource.Success -> {
                    _uiStatePokemon.value = AppUiState.Success(result.item)
                }
            }
        }
    }

    fun getLocalPokemon() = viewModelScope.launch {
        pokemonLocalUseCase.invoke().collect { result ->
            when(result) {
                is AppResource.Loading -> {
                    _uiStateLocalPokemon.value = AppUiState.Loading()
                }

                is AppResource.Error -> {
                    _uiStateLocalPokemon.value = AppUiState.Error(result.throwable)
                }

                is AppResource.Success -> {
                    _uiStateLocalPokemon.value = AppUiState.Success(result.item)
                }
            }
        }
    }

    fun getPokemonDetail(pokemonID: Int) = viewModelScope.launch {
        pokemonDetailUseCase.invoke(pokemonID).collect { result ->
            when(result) {
                is AppResource.Loading -> {
                    _uiStatePokemonDetail.value = AppUiState.Loading()
                }

                is AppResource.Error -> {
                    _uiStatePokemonDetail.value = AppUiState.Error(result.throwable)
                }

                is AppResource.Success -> {
                    _uiStatePokemonDetail.value = AppUiState.Success(result.item)
                }
            }
        }
    }

    fun saveLocalPokemonFavorite(pokemon: Pokemon) = viewModelScope.launch {
        savePokemonUseCase.invoke(pokemon).collect { result ->
            when(result) {
                is AppResource.Loading -> {
                    _uiStateLocalPokemonFavorite.value = AppUiState.Loading()
                }

                is AppResource.Error -> {
                    _uiStateLocalPokemonFavorite.value = AppUiState.Error(result.throwable)
                }

                is AppResource.Success -> {
                    _uiStateLocalPokemonFavorite.value = AppUiState.Success(result.item)
                }
            }
        }
    }


    fun savePokemonSelect(pokemons: PokemonsDetailResponse, url: String, favorite: Boolean) {
        savePokemonDetail(
            PokemonDetailEntity(
                pokemons.name,
                url,
                pokemons.id,
                pokemons.base_experience,
                pokemons.height,
                pokemons.order,
                pokemons.weight,
                favorite = favorite
            )
        )
    }

    private fun savePokemonDetail(pokemon: PokemonDetailEntity) {
        repositoryLocal.insert(pokemon)
    }
}
