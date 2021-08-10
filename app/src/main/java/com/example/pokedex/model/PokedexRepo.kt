package com.example.pokedex.model

import com.example.pokedex.network.JsonPokemonApi
import com.example.pokedex.network.Pokedex.Pokedex
import com.example.pokedex.network.Pokemon.Pokemon
import io.reactivex.Observable

class PokedexRepo(private val jsonPokemonApi: JsonPokemonApi) {
    fun getPokemon(pokemonId: Int): Observable<Pokemon> = jsonPokemonApi.getPokemon(pokemonId)
    fun getPokedex(): Observable<Pokedex> = jsonPokemonApi.getPokedex()
}