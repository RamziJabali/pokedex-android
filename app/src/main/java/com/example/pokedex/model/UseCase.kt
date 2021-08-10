package com.example.pokedex.model

import com.example.pokedex.network.Pokedex.Pokedex
import com.example.pokedex.network.Pokemon.Pokemon
import io.reactivex.Observable

class UseCase(private val pokedexRepo: PokedexRepo) {
    fun getPokemon(pokemonId: Int): Observable<Pokemon> = pokedexRepo.getPokemon(pokemonId)
    fun getPokedex(): Observable<Pokedex> = pokedexRepo.getPokedex()
}