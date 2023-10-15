package com.android.upax_prueba_android.data.model

import com.google.gson.annotations.SerializedName

data class PokemonsDetailResponse(
    @SerializedName("id")
    var id: Int,
    @SerializedName("name")
    var name: String,
    @SerializedName("base_experience")
    var base_experience: Int,
    @SerializedName("height")
    var height: Int,
    @SerializedName("is_default")
    var is_default: Boolean,
    @SerializedName("order")
    var order: Int,
    @SerializedName("weight")
    var weight: Int,
    @SerializedName("abilities")
    var abilities: ArrayList<AbilitiesModel>,
    @SerializedName("forms")
    var forms: ArrayList<NameAndUrlGenericModel>,
    @SerializedName("game_indices")
    var game_indices: ArrayList<GameIndicesModel>,
    @SerializedName("held_items")
    var held_items: ArrayList<HeldItemsModel>,
    @SerializedName("moves")
    var moves: ArrayList<MovesModel>,
    @SerializedName("species")
    var species: NameAndUrlGenericModel,
    @SerializedName("sprites")
    var sprites: SpritesModel,
)

data class SpritesModel(
    @SerializedName("back_default")
    var back_default: String,
    @SerializedName("back_female")
    var back_female: String,
    @SerializedName("back_shiny")
    var back_shiny: String,
    @SerializedName("back_shiny_female")
    var back_shiny_female: String,
    @SerializedName("front_default")
    var front_default: String,
    @SerializedName("front_female")
    var front_female: String,
    @SerializedName("front_shiny")
    var front_shiny: String,
    @SerializedName("front_shiny_female")
    var front_shiny_female: String,
    @SerializedName("other")
    var other: OtherModel,
    @SerializedName("versions")
    var versions: VersionsModel,
)

data class VersionsModel(
    @SerializedName("generation-i")
    var generation_i: GenerationIModel,
    @SerializedName("generation-ii")
    var generation_ii: HomeModel,
    @SerializedName("generation-iii")
    var generation_iii: OfficialArtworkModel,
    @SerializedName("generation-iv")
    var generation_iv: DreamWorldModel,
    @SerializedName("generation-v")
    var generation_v: HomeModel,
    @SerializedName("generation-vi")
    var generation_vi: OfficialArtworkModel,
    @SerializedName("generation-vii")
    var generation_vii: DreamWorldModel,
    @SerializedName("generation-viii")
    var generation_viii: HomeModel,
)

data class GenerationIModel(
    @SerializedName("red-blue")
    var red_blue: RedBlueModel,
    @SerializedName("yellow")
    var yellow: RedBlueModel
)
data class RedBlueModel(
    @SerializedName("back_default")
    var back_default: String,
    @SerializedName("back_gray")
    var back_gray: String,
    @SerializedName("back_transparent")
    var back_transparent: String,
    @SerializedName("front_default")
    var front_default: String,
    @SerializedName("front_gray")
    var front_gray: String,
    @SerializedName("front_transparent")
    var front_transparent: String,
)

data class OtherModel(
    @SerializedName("dream_world")
    var dream_world: DreamWorldModel,
    @SerializedName("home")
    var home: HomeModel,
    @SerializedName("official-artwork")
    var official_artwork: OfficialArtworkModel,
)

data class OfficialArtworkModel(
    @SerializedName("front_default")
    var front_default: String,
    @SerializedName("front_shiny")
    var front_shiny: String,
)

data class HomeModel(
    @SerializedName("front_default")
    var front_default: String,
    @SerializedName("front_female")
    var front_female: String,
    @SerializedName("front_shiny")
    var front_shiny: String,
    @SerializedName("front_shiny_female")
    var front_shiny_female: String,
)

data class DreamWorldModel(
    @SerializedName("front_default")
    var front_default: String,
    @SerializedName("front_female")
    var front_female: String,
)

data class MovesModel(
    @SerializedName("move")
    var move: NameAndUrlGenericModel,
    @SerializedName("version_group_details")
    var version_group_details: ArrayList<VersionGroupDetailsModel>,
    @SerializedName("version_group")
    var version_group: NameAndUrlGenericModel,
)

data class VersionGroupDetailsModel(
    @SerializedName("level_learned_at")
    var level_learned_at: Int,
    @SerializedName("move_learn_method")
    var move_learn_method: NameAndUrlGenericModel,
)

data class HeldItemsModel(
    @SerializedName("item")
    var item: NameAndUrlGenericModel,
    @SerializedName("version_details")
    var version_details: ArrayList<VersionDetailsModel>,
)

data class VersionDetailsModel(
    @SerializedName("rarity")
    var rarity: Int,
    @SerializedName("version")
    var version: NameAndUrlGenericModel,
)

data class GameIndicesModel(
    @SerializedName("game_index")
    var is_hidden: Int,
    @SerializedName("version")
    var version: NameAndUrlGenericModel,
)

data class AbilitiesModel(
    @SerializedName("is_hidden")
    var is_hidden: Boolean,
    @SerializedName("ability")
    var ability: NameAndUrlGenericModel,
    @SerializedName("slot")
    var slot: Int,
)

data class NameAndUrlGenericModel(
    @SerializedName("name")
    var name: String,
    @SerializedName("url")
    var url: String,
)