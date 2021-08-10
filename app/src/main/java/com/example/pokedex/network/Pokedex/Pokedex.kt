package com.example.pokedex.network.Pokedex

import com.google.gson.annotations.SerializedName

data class Pokedex(
    @SerializedName("results")
    val pokedex: Array<BasePokemon> = emptyArray()
)