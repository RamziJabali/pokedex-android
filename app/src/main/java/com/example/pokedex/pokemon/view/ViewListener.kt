package com.example.pokedex.pokemon.view

import com.example.pokedex.pokemon.viewmodel.PokemonViewState

interface ViewListener {
    fun setNewViewState(pokemonViewState: PokemonViewState)
}