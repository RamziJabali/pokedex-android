package com.example.pokedex.pokedex.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.pokedex.model.UseCase
import com.example.pokedex.network.Pokedex.Pokedex
import com.example.pokedex.pokedex.view.GridProperties
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
        makeApiCall()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    private fun makeApiCall() {
        compositeDisposable.add(
            useCase.getPokedex()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { pokedex -> onSuccess(pokedex) },
                    { error -> onFailure(error.localizedMessage) })
        )
    }

    private fun onSuccess(pokedex: Pokedex) {
        for (index in pokedex.pokedex.indices) {
            val idString = getIdFromURL(pokedex.pokedex[index].pokemonURL)
            viewState.gridProperties.add(
                GridProperties(
                    itemText = pokedex.pokedex[index].pokemonName,
                    itemOrderNumber = idString.toInt(),
                    itemOrderNumberString = idString))
        }
        invalidateView()
    }

    private fun getIdFromURL(url: String): String {
        var fixedId = ""
        var index = url.length -2
        while(url[index].isDigit()) {
            fixedId = "${url[index]}$fixedId"
        }
//        while (fixedId.length < 3) {
//            fixedId = "0$fixedId"
//        }
        return fixedId.padStart(3,'0')
    }

    private fun onFailure(localizedMessage: String?) {
        Log.e("ViewModel", localizedMessage!!)
    }

    private fun invalidateView() {
        viewStateObservable.onNext(viewState)
    }

}