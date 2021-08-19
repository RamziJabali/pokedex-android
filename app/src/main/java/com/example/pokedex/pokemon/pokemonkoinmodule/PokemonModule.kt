package com.example.pokedex.pokemon.pokemonkoinmodule

import com.example.pokedex.pokemon.viewmodel.PokemonViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val pokemonModule = module {
    viewModel { PokemonViewModel(get()) }
}