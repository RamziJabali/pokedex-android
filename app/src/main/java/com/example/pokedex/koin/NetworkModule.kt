package com.example.pokedex.koin

import com.example.pokedex.network.JsonPokemonApi
import com.google.gson.Gson
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://pokeapi.co/"

val networkModule = module {

    single<JsonPokemonApi> { get<Retrofit>().create(JsonPokemonApi::class.java) }

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(get<OkHttpClient>())
            .addConverterFactory(get<GsonConverterFactory>())
            .addCallAdapterFactory(get<RxJava2CallAdapterFactory>())
            .build()
    }

    single<OkHttpClient> {
        OkHttpClient.Builder().build()
    }

    single<GsonConverterFactory> {
        GsonConverterFactory.create()
    }

    single<Gson> {
        Gson()
    }

    single<RxJava2CallAdapterFactory> {
        RxJava2CallAdapterFactory.create()
    }
}

