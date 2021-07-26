package com.example.pokedex

import android.app.Application
import com.example.pokedex.koin.networkModule
import com.example.pokedex.koin.pokedexModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class PokedexApplication: Application() {
    private val modules = listOf(networkModule, pokedexModule)

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@PokedexApplication)
            modules(modules)
        }
    }
}