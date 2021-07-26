package com.example.pokedex.koin

import com.example.pokedex.model.PokedexRepo
import com.example.pokedex.network.JsonPokemonApi
import org.koin.dsl.module

val pokedexModule = module {

    single<PokedexRepo> { PokedexRepo(get<JsonPokemonApi>()) }
}