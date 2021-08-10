package com.example.pokedex.network.Pokedex

import com.google.gson.annotations.SerializedName

data class BasePokemon(
    @SerializedName("name")
    val pokemonName: String = "",
    @SerializedName("url")
    val pokemonURL: String  = ""
)