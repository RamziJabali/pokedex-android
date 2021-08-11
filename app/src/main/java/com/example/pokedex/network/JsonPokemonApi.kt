package com.example.pokedex.network

import com.example.pokedex.network.Pokedex.Pokedex
import com.example.pokedex.network.Pokemon.Pokemon
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface JsonPokemonApi {

    @GET("pokemon/{id}")
    fun getPokemon(@Path("id") id: Int): Observable<Pokemon>

//    @GET("pokemon?limit={limit}&offset=0")
    @GET("pokemon")
    fun getPokedex(@Query("limit") limit: Int, @Query("offset") offset: Int): Observable<Pokedex>
}