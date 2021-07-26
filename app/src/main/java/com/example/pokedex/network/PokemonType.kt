package com.example.pokedex.network

import com.google.gson.annotations.SerializedName

data class PokemonType(
    @SerializedName("slot")
    val pokemonSlotNumber: Int,
    @SerializedName("type")
    val pokemonSpecificType: PokemonTypeName

)
