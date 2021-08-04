package com.example.pokedex.pokedex.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.pokedex.model.UseCase
import com.example.pokedex.pokedex.view.GridProperties
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

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
        val boardProperty = GridProperties(
                itemText = pokemon,
                itemOrderNumber = callNumber
        )
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