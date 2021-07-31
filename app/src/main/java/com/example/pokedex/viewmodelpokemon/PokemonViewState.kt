package com.example.pokedex.viewmodelpokemon

import com.example.pokedex.network.PokemonStats
import com.example.pokedex.network.PokemonType

class PokemonViewState(
    val pokemonName: String = "",
    val pokemonOrderNumber: Int = -1,
    val pokemonStats: Array<PokemonStats> = emptyArray(),
    val pokemonType: Array<PokemonType> = emptyArray(),
)