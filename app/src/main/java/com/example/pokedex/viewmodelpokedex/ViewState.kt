package com.example.pokedex.viewmodelpokedex

import com.example.pokedex.pokedexview.GridProperties
import com.example.pokedex.viewmodelpokemon.PokemonViewState

data class ViewState(
    var gridProperties: ArrayList<GridProperties> = ArrayList(),
    var pokemonArrayList: ArrayList<PokemonViewState> = ArrayList()
)