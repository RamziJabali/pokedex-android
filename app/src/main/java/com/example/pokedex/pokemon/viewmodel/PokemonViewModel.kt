package com.example.pokedex.pokemon.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.pokedex.model.UseCase
import com.example.pokedex.network.Pokemon
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
                    { pokemon -> onSuccess(pokemon, pokemonId) },
                    { error -> onFailure(error.localizedMessage) })
        )
    }

    private fun onSuccess(pokemon: Pokemon, pokemonId: Int) {
        pokemonViewState = pokemonViewState.copy(
            pokemonImageURL = getCompleteImageURL(pokemonId.toString()),
            pokemonName = pokemon.pokemonName.capitalize(),
            pokemonOrderNumber = pokemon.pokemonOrderNumber,
            pokemonStat1 = pokemon.pokemonStats[0].pokemonBaseStat,
            pokemonStat2 = pokemon.pokemonStats[1].pokemonBaseStat,
            pokemonStat3 = pokemon.pokemonStats[2].pokemonBaseStat,
            pokemonStat4 = pokemon.pokemonStats[3].pokemonBaseStat,
            pokemonStat5 = pokemon.pokemonStats[4].pokemonBaseStat,
            pokemonStat6 = pokemon.pokemonStats[5].pokemonBaseStat,
            pokemonStat1String = pokemon.pokemonStats[0].pokemonStatType.statTypeName.toUpperCase(),
            pokemonStat2String = pokemon.pokemonStats[1].pokemonStatType.statTypeName.toUpperCase(),
            pokemonStat3String = pokemon.pokemonStats[2].pokemonStatType.statTypeName.toUpperCase(),
            pokemonStat4String = pokemon.pokemonStats[3].pokemonStatType.statTypeName.toUpperCase(),
            pokemonStat5String = pokemon.pokemonStats[4].pokemonStatType.statTypeName.toUpperCase(),
            pokemonStat6String = pokemon.pokemonStats[5].pokemonStatType.statTypeName.toUpperCase(),
            pokemonStatType1String = pokemon.pokemonTypes[0].pokemonSpecificType.pokemonTypeName.toUpperCase()
        )
        if (pokemon.pokemonTypes.size > 1) {
            pokemonViewState =
                pokemonViewState.copy(pokemonStatType2String = pokemon.pokemonTypes[1].pokemonSpecificType.pokemonTypeName.toUpperCase())
        }
        invalidateView()
    }

    private fun getCompleteImageURL(id: String): String {
        var newId = id
        while (newId.length < 3) {
            newId = "0$newId"
        }
        return pokemonViewState.pokemonImageURL + newId + pokemonViewState.pokemonImageDotPNG
    }

    private fun onFailure(localizedMessage: String?) {
        Log.e("PokemonViewModel", localizedMessage!!)
    }

    private fun invalidateView() {
        pokemonViewStateObservable.onNext(pokemonViewState)
    }
}