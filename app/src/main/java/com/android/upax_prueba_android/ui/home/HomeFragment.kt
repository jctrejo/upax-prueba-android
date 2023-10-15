package com.android.upax_prueba_android.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.android.upax_prueba_android.R
import com.android.upax_prueba_android.core.AppUiState
import com.android.upax_prueba_android.core.Constants.EMPTY
import com.android.upax_prueba_android.core.Constants.SIZE_25
import com.android.upax_prueba_android.data.model.Pokemon
import com.android.upax_prueba_android.data.model.PokemonsDetailResponse
import com.android.upax_prueba_android.databinding.FragmentHomeBinding
import com.android.upax_prueba_android.ui.home.PokemonAdapter.OnClickListener
import com.android.upax_prueba_android.util.LoadingDialog
import com.android.upax_prueba_android.util.PokemonDialog
import com.android.upax_prueba_android.util.extension.getPokemonIDOfUrl
import com.android.upax_prueba_android.util.extension.internetCheck
import com.android.upax_prueba_android.util.extension.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var contextBinding: Context
    private lateinit var pokemonAdapter: PokemonAdapter
    private lateinit var loader: LoadingDialog
    private lateinit var pokemonDialog: PokemonDialog
    private val viewModel: HomeViewModel by viewModels()
    private var localUrl: String? = EMPTY
    private var sizeList: Int? = 0
    private var sizeLocalList: Int? = 0
    private var isFavorite: Boolean? = false
    private var isLoaderScroll: Boolean? = false
    private var sizeSumPokemonList: Int? = 0
    private var isFirstLoader: Boolean? = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return if (this::binding.isInitialized) {
            binding.root
        } else {
            binding = FragmentHomeBinding.inflate(layoutInflater)
            contextBinding = binding.root.context
            binding.root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() = with(binding) {
        loader = LoadingDialog(binding.root.context)
        pokemonDialog = PokemonDialog(contextBinding)

        setupAdapter()
        setupObserverLocalPokemon()
        setupObserverPokemon()
        setupObserverPokemonDetail(
            isShowFragment = false,
            isShowDialog = false,
            favorite = false
        )

        containerRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && isLoaderScroll == true) {
                    sizeSumPokemonList = sizeLocalList?.plus(SIZE_25)
                    isLoaderScroll = false
                    if (!contextBinding.internetCheck()) {
                        contextBinding.toast(getString(R.string.need_internet))
                    } else {
                        if ((sizeSumPokemonList ?: 0) > (sizeLocalList ?: 0)) {
                            viewModel.getPokemon(sizeSumPokemonList ?: SIZE_25)
                        }
                    }
                }
            }
        })

        viewModel.getLocalPokemon()
    }

    private fun setupAdapter() = with(binding) {
        pokemonAdapter = PokemonAdapter(OnClickListener { pokemon, url, favorite ->
            val id = pokemon.id
            localUrl = url
            if (contextBinding.internetCheck()) {
                setupObserverPokemonDetail(
                    isShowFragment = false,
                    isShowDialog = true,
                    favorite = false
                )
                viewModel.getPokemonDetail(id)
            } else {
                pokemonDialog.showDialog(
                    url,
                    pokemon,
                    onAccept = {})
            }
        }, onClickListenerPokemonDetail = OnClickListener { pokemon, url, favorite ->
            localUrl = url
            isFavorite = favorite
            goToPokemonDetailFragment(
                pokemon.id,
                pokemon.name,
                pokemon.height,
                pokemon.weight,
                pokemon.is_default,
                url,
                isFavorite ?: false
            )
        }, onClickListenerPokemonFavorite = OnClickListener { pokemon, url, fav ->
            localUrl = url
            isFavorite = fav
            val id = pokemon.id

            setupObserverPokemonDetail(
                isShowFragment = false,
                isShowDialog = false,
                favorite = fav
            )
            viewModel.getPokemonDetail(id)
        })

        containerRecyclerView.adapter = pokemonAdapter
    }

    private fun setupObserverPokemon() {
        viewModel.uiStatePokemon.observe(viewLifecycleOwner) { state ->
            when(state) {
                is AppUiState.Loading -> {
                    loader.show()
                }

                is AppUiState.Error -> {
                    loader.hide()
                }

                is AppUiState.Success -> {
                    sizeList = state.data.results.size
                    println("Success... ${state.data.results}")
                    setupSavePokemonDetail(state.data.results)
                }

                else -> {}
            }
        }
    }

    private fun setupObserverLocalPokemon() {
        viewModel.uiStateLocalPokemon.observe(viewLifecycleOwner) { state ->
            when(state) {
                is AppUiState.Loading -> {
                    loader.show()
                }

                is AppUiState.Error -> {
                    loader.hide()
                }

                is AppUiState.Success -> {
                    loader.hide()
                    println("Success... ${state.data.results}")
                    sizeLocalList = state.data.results.size
                    pokemonAdapter.submitList(state.data.results)
                    isLoaderScroll = true
                    if (state.data.results.isEmpty()) {
                        viewModel.getPokemon(SIZE_25)
                    }
                }

                else -> {}
            }
        }
    }

    private fun setupObserverLocalPokemonFavorite() {
        viewModel.uiStateLocalPokemonFavorite.observe(viewLifecycleOwner) { state ->
            when(state) {
                is AppUiState.Loading -> {
                    loader.show()
                }

                is AppUiState.Error -> {
                    loader.hide()
                }

                is AppUiState.Success -> {
                    loader.hide()
                    println(state.data)
                }

                else -> {}
            }
        }
    }

    private fun setupObserverPokemonDetail(
        isShowFragment: Boolean = false,
        isShowDialog: Boolean = false,
        favorite: Boolean = false,
    ) {
        viewModel.uiStatePokemonDetail.observe(viewLifecycleOwner) { state ->
            when(state) {
                is AppUiState.Loading -> {
                    println("Loading...")
                }

                is AppUiState.Error -> {
                    loader.hide()
                }

                is AppUiState.Success -> {
                    if (isShowFragment) {
                        //viewModel.savePokemonSelect(state.data, localUrl ?: EMPTY, false)
                        showDetailFragment(state.data, localUrl ?: EMPTY, isFavorite ?: false)
                    } else if (isShowDialog) {
                        showPokemonDialog(state.data, localUrl ?: EMPTY)
                    } else if (favorite) {
                        val pokemon = state.data

                        val pokemonAddFavorite = Pokemon(
                            id = pokemon.id,
                            name = pokemon.name,
                            url = localUrl ?: EMPTY,
                            base_experience = pokemon.base_experience,
                            height = pokemon.height,
                            is_default = pokemon.is_default,
                            order = pokemon.order,
                            weight = pokemon.weight,
                            favorite = isFavorite ?: false
                        )
                        saveLocalFavorite(pokemonAddFavorite)
                    }
                    if (sizeSumPokemonList == state.data.id) {
                        viewModel.getLocalPokemon()
                    } else if (state.data.id == 25 && isFirstLoader == false) {
                        viewModel.getLocalPokemon()
                        isFirstLoader = true
                    }
                    println("dede")
                }

                else -> {}
            }
        }
    }

    private fun setupSavePokemonDetail(results: List<Pokemon>) {
        results.forEach {
            viewModel.getPokemonDetail(
                if (contextBinding.internetCheck()) it.url.getPokemonIDOfUrl() else it.id + 1
            )
        }
    }

    private fun saveLocalFavorite(pokemon: Pokemon) {
        val pokemonAddFavorite = Pokemon(
            id = pokemon.id,
            name = pokemon.name,
            url = localUrl ?: EMPTY,
            base_experience = pokemon.base_experience,
            height = pokemon.height,
            is_default = pokemon.is_default,
            order = pokemon.order,
            weight = pokemon.weight,
            favorite = isFavorite ?: false
        )
        setupObserverLocalPokemonFavorite()
        viewModel.saveLocalPokemonFavorite(pokemonAddFavorite)
    }

    private fun showDetailFragment(
        pokemon: PokemonsDetailResponse,
        url: String,
        favorite: Boolean,
    ) {
        goToPokemonDetailFragment(
            pokemon.id,
            pokemon.name,
            pokemon.height,
            pokemon.weight,
            pokemon.is_default,
            url,
            favorite
        )
    }

    private fun goToPokemonDetailFragment(
        id: Int,
        name: String,
        height: Int,
        weight: Int,
        isDefault: Boolean,
        url: String,
        favorite: Boolean,
    ) {
        findNavController().navigate(
            HomeFragmentDirections.actionHomeFragmentToPokemonDetailFragment(
                idPokemon = id,
                name = name,
                height = height,
                weight = weight,
                isDefault = isDefault,
                url = url,
                isFavorite = favorite
            )
        )
    }

    private fun showPokemonDialog(data: PokemonsDetailResponse, url: String) {
        val pokemon = Pokemon(
            id = data.id,
            name = data.name,
            base_experience = data.base_experience,
            height = data.height,
            is_default = data.is_default,
            order = data.order,
            weight = data.weight
        )

        pokemonDialog.showDialog(
            url,
            pokemon,
            onAccept = {})
    }
}
