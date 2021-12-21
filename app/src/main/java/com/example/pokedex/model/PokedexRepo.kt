package com.example.pokedex.model

import com.example.pokedex.network.PokemonApi
import com.example.pokedex.network.Pokedex.Pokedex
import com.example.pokedex.network.Pokemon.Pokemon
import io.reactivex.Observable
import io.reactivex.Single

class PokedexRepo(private val pokemonApi: PokemonApi) {
    fun getPokemon(pokemonId: Int): Observable<Pokemon> = pokemonApi.getPokemon(pokemonId)
    fun getPokedex( offset: Int, limit: Int): Single<Pokedex> = pokemonApi.getPokedex(offset, limit)
}