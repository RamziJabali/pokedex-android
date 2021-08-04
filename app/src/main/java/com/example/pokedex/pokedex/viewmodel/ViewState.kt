package com.example.pokedex.pokedex.viewmodel

import com.example.pokedex.pokedex.view.GridProperties
import com.example.pokedex.pokemon.viewmodel.PokemonViewState

data class ViewState(
    var gridProperties: ArrayList<GridProperties> = ArrayList(),
)