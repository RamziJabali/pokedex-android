package com.example.pokedex.network

import com.google.gson.annotations.SerializedName

data class PokemonStatStype(
    @SerializedName("name")
    val statTypeName: String
)
