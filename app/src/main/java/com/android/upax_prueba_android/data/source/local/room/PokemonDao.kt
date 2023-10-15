package com.android.upax_prueba_android.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.android.upax_prueba_android.data.model.PokemonsListEntity

@Dao
interface PokemonDao {
    @Insert
    fun insert(pokemon: PokemonDetailEntity)

    @Update
    fun update(vararg pokemon: PokemonDetailEntity)

    @Delete
    fun delete(vararg pokemon: PokemonDetailEntity)

    @Query("SELECT * FROM " + PokemonDetailEntity.TABLE_NAME + " ORDER BY name, url")
    fun getPokemon(): LiveData<List<PokemonDetailEntity>>

    @Query("SELECT * FROM pokemonslistentity")
    suspend fun getAllPokemons(): List<PokemonsListEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun savePokemon(pokemon: PokemonsListEntity)

}