package com.example.pokedex.network

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface JsonPokemonApi {

    @GET("api/v2/pokemon/{id}")
    fun getPokemon(@Path("id") id: Int): Observable<Pokemon>
}