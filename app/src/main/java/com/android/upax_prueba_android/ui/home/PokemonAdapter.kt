package com.android.upax_prueba_android.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.upax_prueba_android.R
import com.android.upax_prueba_android.data.model.Pokemon
import com.android.upax_prueba_android.data.model.ProfileModel
import com.android.upax_prueba_android.databinding.ItemPokemonBinding
import com.android.upax_prueba_android.util.extension.show
import com.android.upax_prueba_android.util.extension.toCapitalLetter
import com.android.upax_prueba_android.util.extension.urlPokemon
import com.android.upax_prueba_android.util.extension.validateImageProfile

class PokemonAdapter(
    private val onClickListener: OnClickListener,
    private val onClickListenerPokemonDetail: OnClickListener,
    private val onClickListenerPokemonFavorite: OnClickListener,

    ) :
    ListAdapter<Pokemon, PokemonAdapter.BeerViewHolder>(BeerComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeerViewHolder {
        val binding =
            ItemPokemonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BeerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BeerViewHolder, position: Int) {
        val pokemon = getItem(position)

        val id = pokemon.id
        val url = id.toString().urlPokemon()
        var isFavorite: Boolean? = false
        var isFavoriteModified: Boolean? = false

        isFavorite = pokemon.favorite
        holder.binding.selectContainer.setOnClickListener {
            onClickListener.onClick(pokemon, url, pokemon.favorite)
        }

        holder.binding.containerCardView.setOnClickListener {
            onClickListenerPokemonDetail.onClick(
                pokemon, url,
                if (isFavoriteModified == true) isFavorite ?: false else pokemon.favorite
            )
        }

        holder.binding.favoriteImageView.setOnClickListener {
            isFavorite = if (isFavorite == true) {
                holder.binding.favoriteImageView.setImageResource(R.drawable.ic_heart)
                isFavoriteModified = true
                false
            } else {
                holder.binding.favoriteImageView.setImageResource(R.drawable.ic_heart_on)
                isFavoriteModified = true
                true
            }
            onClickListenerPokemonFavorite.onClick(pokemon, pokemon.url, isFavorite ?: false)
        }

        holder.onBind(getItem(position), position)
    }

    class OnClickListener(
        val clickListener: (pokemon: Pokemon, url: String, isFavorite: Boolean) -> Unit,
    ) {
        fun onClick(pokemon: Pokemon, url: String, isFavorite: Boolean) =
            clickListener(pokemon, url, isFavorite)
    }

    class BeerViewHolder(val binding: ItemPokemonBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(pokemon: Pokemon, pokemonNumber: Int) = with(binding) {
            titleTextView.text = pokemon.name.toCapitalLetter()
            val pokemonID = pokemonNumber + 1
            val model = ProfileModel(pokemonNumber, pokemon.name, pokemonID.toString().urlPokemon())

            postImageView.validateImageProfile(
                model,
                placeholderTextLayout,
                placeholderTextView,
                null,
                null,
                binding.root.context
            )
            postImageView.show()
            favoriteImageView.setImageResource(if (pokemon.favorite) R.drawable.ic_heart_on else R.drawable.ic_heart)
        }
    }

    object BeerComparator : DiffUtil.ItemCallback<Pokemon>() {
        override fun areItemsTheSame(
            oldItem: Pokemon,
            newItem: Pokemon,
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: Pokemon,
            newItem: Pokemon,
        ): Boolean {
            return oldItem.name == newItem.name
        }
    }
}
