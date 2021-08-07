package com.example.pokedex.pokedex.viewmodel

import android.content.Context
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModel
import com.example.pokedex.model.UseCase
import com.example.pokedex.pokedex.view.GridProperties
import com.example.pokedex.pokemon.view.PokemonActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import java.util.*

class ViewModel(private var useCase: UseCase) : ViewModel() {


    val viewStateObservable = BehaviorSubject.create<ViewState>()

    private var viewState = ViewState()
    private var compositeDisposable = CompositeDisposable()

    fun startApplication() {
        for (apiCallNumber in (0 until 21)) {
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
                    { pokemon -> onSuccess(pokemon.pokemonName, callNumber) },
                    { error -> onFailure(error.localizedMessage) })
        )
    }

    private fun onSuccess(pokemon: String, callNumber: Int) {
        var itemOrderNumberString = callNumber.toString()
        var boardProperty = GridProperties(
            itemText = pokemon.capitalize(Locale(pokemon)),
            itemOrderNumber = callNumber
        )

        while (itemOrderNumberString.length < 3) {
            itemOrderNumberString = "0$itemOrderNumberString"
        }
        boardProperty = boardProperty.copy(itemOrderNumberString = itemOrderNumberString)

        viewState.gridProperties.add(boardProperty)
        invalidateView()
    }

    private fun onFailure(localizedMessage: String?) {
        Log.e("ViewModel", localizedMessage!!)
    }

    private fun invalidateView() {
        viewStateObservable.onNext(viewState)
    }

}