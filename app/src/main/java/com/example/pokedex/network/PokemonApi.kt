package com.example.pokedex.network

import com.example.pokedex.network.Pokedex.Pokedex
import com.example.pokedex.network.Pokemon.Pokemon
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApi {

    @GET("pokemon/{id}")
    fun getPokemon(@Path("id") id: Int): Observable<Pokemon>

//    @GET("pokemon?limit={limit}&offset=0")
    @GET("pokemon")
    fun getPokedex(@Query("offset") offset: Int, @Query("limit") limit: Int): Single<Pokedex>
}