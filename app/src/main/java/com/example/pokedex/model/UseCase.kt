package com.example.pokedex.model

import com.example.pokedex.model.PokedexRepo
import com.example.pokedex.network.Pokemon
import io.reactivex.Observable

class UseCase(private val pokedexRepo: PokedexRepo) {
    fun getPokedex(pokemonId: Int): Observable<Pokemon> = pokedexRepo.getPokemon(pokemonId)
}