package com.example.pokedex.pokedex.viewmodel

import com.example.pokedex.pokedex.view.GridProperties
import com.example.pokedex.pokemon.viewmodel.PokemonViewState

data class ViewState(
    var gridProperties: ArrayList<GridProperties> = ArrayList(),
    var launchActivity: Boolean = false,
    var pokeId: Int = -1
)