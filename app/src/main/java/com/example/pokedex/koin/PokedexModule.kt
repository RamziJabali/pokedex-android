package com.example.pokedex.koin

import com.example.pokedex.model.PokedexRepo
import com.example.pokedex.model.UseCase
import com.example.pokedex.network.JsonPokemonApi
import com.example.pokedex.pokedex.viewmodel.ViewModel
import com.example.pokedex.pokemon.viewmodel.PokemonViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val pokedexModule = module {

    viewModel { ViewModel(get()) }
    viewModel { PokemonViewModel(get()) }
    single<UseCase> { UseCase(get<PokedexRepo>()) }
    single<PokedexRepo> { PokedexRepo(get<JsonPokemonApi>()) }
}