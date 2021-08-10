package com.example.pokedex.network.Pokemon

import com.google.gson.annotations.SerializedName

data class PokemonStatStype(
    @SerializedName("name")
    val statTypeName: String
)
