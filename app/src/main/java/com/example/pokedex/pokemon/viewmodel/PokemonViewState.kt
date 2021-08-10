package com.example.pokedex.pokemon.viewmodel

data class PokemonViewState(
    val pokemonImageURL: String ="https://assets.pokemon.com/assets/cms2/img/pokedex/full/",
    val pokemonImageDotPNG: String =".png",
    val pokemonName: String = "",
    val pokemonOrderNumber: Int = -1,

    val pokemonStat1: Int = -1,
    val pokemonStat2: Int = -1,
    val pokemonStat3: Int = -1,
    val pokemonStat4: Int = -1,
    val pokemonStat5: Int = -1,
    val pokemonStat6: Int = -1,

    val pokemonStat1String: String = "",
    val pokemonStat2String: String = "",
    val pokemonStat3String: String = "",
    val pokemonStat4String: String = "",
    val pokemonStat5String: String = "",
    val pokemonStat6String: String = "",

    val pokemonStatType1String: String = "",
    val pokemonStatType2String: String = ""
)