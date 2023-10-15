package com.android.upax_prueba_android.data.source.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.android.upax_prueba_android.data.model.PokemonsListEntity

@Database(entities = [PokemonDetailEntity::class, PokemonsListEntity::class], version = 1)
abstract class PokemonDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao

    companion object {
        private const val DATABASE_NAME = "score_database"

        @Volatile
        private var INSTANCE: PokemonDatabase? = null

        fun getDatabase(context: Context): PokemonDatabase {
            INSTANCE = INSTANCE ?: Room.databaseBuilder(
                context.applicationContext,
                PokemonDatabase::class.java,
                "pokemon_table"
            ).build()
            return INSTANCE as PokemonDatabase
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}