package com.example.pokedex.pokedex.pokedexkoinmodule

import com.example.pokedex.model.PokedexRepo
import com.example.pokedex.model.UseCase
import com.example.pokedex.network.PokemonApi
import com.example.pokedex.pokedex.viewmodel.ViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val pokedexModule = module {
    viewModel { ViewModel(get()) }
    single<UseCase> { UseCase(get<PokedexRepo>()) }
    single<PokedexRepo> { PokedexRepo(get<PokemonApi>()) }
}