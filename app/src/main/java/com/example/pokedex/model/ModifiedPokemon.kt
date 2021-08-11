package com.example.pokedex.model

data class ModifiedPokemon(
    val pokemonImageURL: String = "",
    val pokemonName: String = "",
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
