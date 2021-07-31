package com.example.pokedex.viewmodelpokedex

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.pokedex.model.UseCase
import com.example.pokedex.network.Pokemon
import com.example.pokedex.pokedexview.GridProperties
import com.example.pokedex.viewmodelpokemon.PokemonViewState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

class ViewModel(private var useCase: UseCase) : ViewModel() {


    val viewStateObservable = BehaviorSubject.create<ViewState>()

    private var viewState = ViewState()
    private var compositeDisposable = CompositeDisposable()

    fun startApplication() {
        for (apiCallNumber in (1 until 20)) {
            makeApiCall(apiCallNumber)
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    private fun makeApiCall(callNumber: Int) {
        compositeDisposable.add(
            useCase.getPokedex(callNumber)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { pokemon -> onSuccess(pokemon, callNumber) },
                    { error -> onFailure(error.localizedMessage) })
        )
    }

    private fun onSuccess(pokemon: Pokemon, callNumber: Int) {
        val boardProperty = GridProperties(
            itemText = pokemon.pokemonName,
            itemOrderNumber = callNumber
        )
        viewState.pokemonArrayList.add(
            PokemonViewState(
                pokemonName = pokemon.pokemonName,
                pokemonOrderNumber = callNumber,
                pokemonStats = pokemon.pokemonStats,
                pokemonType = pokemon.pokemonTypes
            )
        )
        viewState.gridProperties.add(boardProperty)
        invalidateView()
    }

    private fun onFailure(localizedMessage: String?) {
        Log.e("MainActivity", localizedMessage!!)
    }

    private fun invalidateView() {
        viewStateObservable.onNext(viewState)
    }
}