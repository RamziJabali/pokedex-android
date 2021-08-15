package com.example.pokedex.koin

import com.example.pokedex.pokemon.viewmodel.PokemonViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val pokemonModule = module {
    viewModel { PokemonViewModel(get()) }
}