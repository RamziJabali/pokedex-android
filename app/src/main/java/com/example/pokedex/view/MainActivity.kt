package com.example.pokedex.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.pokedex.model.PokedexRepo
import com.example.pokedex.R
import com.example.pokedex.model.UseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.android.get

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val useCase = UseCase(get<PokedexRepo>()).getPokedex(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.v("MainActivity",it.pokemonName)
                    Log.v("MainActivity",it.pokemonOrderNumber.toString())
                    Log.v("MainActivity",it.pokemonStats[1].pokemonStatType.statTypeName)
                    Log.v("MainActivity",it.pokemonTypes[1].pokemonSpecificType.pokemonTypeName)
                },
                        { error -> onFailure(error.localizedMessage) })
    }

    private fun onFailure(localizedMessage: String?) {
        Log.e("MainActivity", localizedMessage!!)
    }

}