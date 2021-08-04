package com.example.pokedex.pokemon.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.pokedex.model.UseCase
import com.example.pokedex.network.Pokemon
import com.example.pokedex.pokedex.viewmodel.ViewState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

class PokemonViewModel(private var useCase: UseCase) : ViewModel() {

    private var pokemonViewState = PokemonViewState()

    private var compositeDisposable = CompositeDisposable()

    val pokemonViewStateObservable = BehaviorSubject.create<PokemonViewState>()

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    fun getInformation(pokemonId: Int) {
        compositeDisposable.add(
                useCase.getPokedex(pokemonId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                { pokemon -> onSuccess(pokemon) },
                                { error -> onFailure(error.localizedMessage) })
        )
    }

    private fun onSuccess(pokemon: Pokemon) {
        pokemonViewState = pokemonViewState.copy(
                pokemonName = pokemon.pokemonName,
                pokemonOrderNumber = pokemon.pokemonOrderNumber,
                pokemonStats = pokemon.pokemonStats,
                pokemonType = pokemon.pokemonTypes
        )
        invalidateView()
    }

    private fun onFailure(localizedMessage: String?) {
        Log.e("PokemonViewModel", localizedMessage!!)
    }

    private fun invalidateView() {
        pokemonViewStateObservable.onNext(pokemonViewState)
    }
}