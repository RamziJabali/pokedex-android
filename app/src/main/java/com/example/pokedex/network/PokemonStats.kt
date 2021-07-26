package com.example.pokedex.network

import com.google.gson.annotations.SerializedName

data class PokemonStats(
    @SerializedName("base_stat")
    val pokemonBaseStat: Int,
    @SerializedName("stat")
    val pokemonStatType: PokemonStatStype
)
