package com.example.pokedex.viewmodelpokemon

class PokemonViewState(
    val pokemonName: String = "",
    val pokemonOrderNumber: Int = -1,
    val pokemonSlotNumber: Array<Int> = emptyArray(),
    val pokemonBaseStat: Array<Int> = emptyArray(),
    val pokemonBaseStatName: Array<String> = emptyArray(),
    val pokemonTypeName: Array<String> = emptyArray()
)