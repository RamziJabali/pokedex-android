package com.example.pokedex.koin

import com.example.pokedex.model.PokedexRepo
import com.example.pokedex.model.UseCase
import com.example.pokedex.network.JsonPokemonApi
import com.example.pokedex.viewmodelpokedex.ViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val pokedexModule = module {

    viewModel { ViewModel(get()) }
    single<UseCase> { UseCase(get<PokedexRepo>()) }
    single<PokedexRepo> { PokedexRepo(get<JsonPokemonApi>()) }
}