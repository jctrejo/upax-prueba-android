package com.android.upax_prueba_android.data.source.local.room

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData

class PokemonRepository(application: Application) {
    private val pokemonDao: PokemonDao = PokemonDatabase.getDatabase(application).pokemonDao()

    fun insert(pokemon: PokemonDetailEntity) {
        InsertAsyncTask(pokemonDao).execute(pokemon)
    }

    fun getPokemons(): LiveData<List<PokemonDetailEntity>> {
        return pokemonDao.getPokemon()
    }

    private class InsertAsyncTask(private val pokemonDao: PokemonDao) :
        AsyncTask<PokemonDetailEntity, Void, Void>() {
        override fun doInBackground(vararg pokemons: PokemonDetailEntity?): Void? {
            for (pokemon in pokemons) {
                if (pokemon != null) pokemonDao.insert(pokemon)
            }
            return null
        }
    }
}
