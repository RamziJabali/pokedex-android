package com.example.pokedex.network

import com.example.pokedex.network.Pokedex.Pokedex
import com.example.pokedex.network.Pokemon.Pokemon
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface JsonPokemonApi {

    @GET("pokemon/{id}")
    fun getPokemon(@Path("id") id: Int): Observable<Pokemon>

    @GET("pokemon?limit=20&offset=0")
    fun getPokedex(): Observable<Pokedex>
}