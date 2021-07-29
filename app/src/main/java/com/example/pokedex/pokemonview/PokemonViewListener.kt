package com.example.pokedex.pokemonview

import com.example.pokedex.viewmodelpokemon.PokemonViewState

interface PokemonViewListener {
    fun setPokemonViewState(pokemonViewState: PokemonViewState)
}