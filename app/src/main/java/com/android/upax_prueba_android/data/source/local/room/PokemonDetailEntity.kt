package com.android.upax_prueba_android.data.source.local.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = PokemonDetailEntity.TABLE_NAME)
data class PokemonDetailEntity(
    @ColumnInfo(name = "name") @NotNull val name: String,
    @ColumnInfo(name = "url") @NotNull val url: String,
    @ColumnInfo(name = "id") @NotNull val id: Int,
    @ColumnInfo(name = "base_experience") @NotNull val base_experience: Int,
    @ColumnInfo(name = "height") @NotNull val height: Int,
    @ColumnInfo(name = "order") @NotNull val order: Int,
    @ColumnInfo(name = "weight") @NotNull val weight: Int,
    @ColumnInfo(name = "favorite") @NotNull val favorite: Boolean,
) {
    companion object {
        const val TABLE_NAME = "pokemon_detail"
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "pokemon_id")
    var pokemonId: Int = 0
}