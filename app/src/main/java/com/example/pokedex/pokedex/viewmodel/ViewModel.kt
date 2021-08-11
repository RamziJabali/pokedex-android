package com.example.pokedex.pokedex.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.pokedex.model.BasePokedex
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
        makeApiCall()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    private fun makeApiCall() {
        compositeDisposable.add(
            useCase.getPokedex(10, 20)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { pokedex -> onSuccess(pokedex) },
                    { error -> onFailure(error.localizedMessage) })
        )
    }

    private fun onSuccess(pokedex: BasePokedex) {
        for (index in pokedex.pokemonId.indices) {
            viewState.gridProperties.add(
                GridProperties(
                    itemText = pokedex.pokemonName[index],
                    itemOrderNumberString = pokedex.pokemonIdString[index],
                    itemOrderNumber = pokedex.pokemonId[index]
                )
            )
        }
        invalidateView()
    }

    private fun onFailure(localizedMessage: String?) {
        Log.e("ViewModel", localizedMessage!!)
    }

    private fun invalidateView() {
        viewStateObservable.onNext(viewState)
    }

}