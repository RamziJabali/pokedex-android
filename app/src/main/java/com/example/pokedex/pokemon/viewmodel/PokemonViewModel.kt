package com.example.pokedex.pokemon.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.pokedex.BuildConfig
import com.example.pokedex.model.ModifiedPokemon
import com.example.pokedex.model.UseCase
import com.example.pokedex.network.Pokemon.Pokemon
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
            useCase.getPokemon(pokemonId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { modifiedPokemon -> onSuccess(modifiedPokemon) },
                    { error -> onFailure(error.localizedMessage) })
        )
    }

    private fun onSuccess(pokemon: ModifiedPokemon) {
        pokemonViewState = pokemonViewState.copy(
            pokemonImageURL = pokemon.pokemonImageURL,
            pokemonName = pokemon.pokemonName,
            pokemonStat1 = pokemon.pokemonStat1,
            pokemonStat2 = pokemon.pokemonStat2,
            pokemonStat3 = pokemon.pokemonStat3,
            pokemonStat4 = pokemon.pokemonStat4,
            pokemonStat5 = pokemon.pokemonStat5,
            pokemonStat6 = pokemon.pokemonStat6,
            pokemonStat1String = pokemon.pokemonStat1String,
            pokemonStat2String = pokemon.pokemonStat2String,
            pokemonStat3String = pokemon.pokemonStat3String,
            pokemonStat4String = pokemon.pokemonStat4String,
            pokemonStat5String = pokemon.pokemonStat5String,
            pokemonStat6String = pokemon.pokemonStat6String,
            pokemonStatType1String = pokemon.pokemonStatType1String,
            pokemonStatType2String = pokemon.pokemonStatType2String
        )
        invalidateView()
    }


    private fun onFailure(localizedMessage: String?) {
        if (BuildConfig.DEBUG) Log.d("PokemonViewModel", localizedMessage!!)
    }

    private fun invalidateView() {
        pokemonViewStateObservable.onNext(pokemonViewState)
    }
}