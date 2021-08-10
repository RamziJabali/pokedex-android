package com.example.pokedex.network.Pokemon

import com.google.gson.annotations.SerializedName

data class PokemonTypeName(
   @SerializedName("name")
   val pokemonTypeName: String
)

