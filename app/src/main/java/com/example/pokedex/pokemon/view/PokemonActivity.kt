package com.example.pokedex.pokemon.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.pokedex.R
import com.example.pokedex.pokemon.viewmodel.PokemonViewModel
import com.example.pokedex.pokemon.viewmodel.PokemonViewState
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.IllegalStateException


class PokemonActivity : AppCompatActivity(), ViewListener {
    companion object {
        val TAG: String = PokemonActivity::class.java.simpleName
        const val EXTRA_POKEMON_ID = "com.example.pokedex.pokedex.view"

        fun newInstance(context: Context, pokeId: Int): Intent {
            return Intent(context, PokemonActivity::class.java).apply {
                putExtra(EXTRA_POKEMON_ID, pokeId)
            }
        }

    }

    private var pokemonId: Int = -1
    private val pokemonViewModel: PokemonViewModel by viewModel()

    private val pokemonEnhancedImageView: ImageView by lazy { findViewById(R.id.pokemonImageEnhanced) }

    private val pokemonType1: TextView by lazy { findViewById(R.id.pokemonTypeOneTextView) }
    private val pokemonType2: TextView by lazy { findViewById(R.id.pokemonTypeTwoTextView) }

    private val pokemonStat1Name: TextView by lazy { findViewById(R.id.statOne) }
    private val pokemonStat2Name: TextView by lazy { findViewById(R.id.statTwo) }
    private val pokemonStat3Name: TextView by lazy { findViewById(R.id.statThree) }
    private val pokemonStat4Name: TextView by lazy { findViewById(R.id.statFour) }
    private val pokemonStat5Name: TextView by lazy { findViewById(R.id.statFive) }
    private val pokemonStat6Name: TextView by lazy { findViewById(R.id.statSix) }

    private val pokemonStat1Value: TextView by lazy { findViewById(R.id.statOneValue) }
    private val pokemonStat2Value: TextView by lazy { findViewById(R.id.statTwoValue) }
    private val pokemonStat3Value: TextView by lazy { findViewById(R.id.statThreeValue) }
    private val pokemonStat4Value: TextView by lazy { findViewById(R.id.statFourValue) }
    private val pokemonStat5Value: TextView by lazy { findViewById(R.id.statFiveValue) }
    private val pokemonStat6Value: TextView by lazy { findViewById(R.id.statSixValue) }

    private val pokemonStatBar1: ProgressBar by lazy { findViewById(R.id.horizontal_loading_bar_1) }
    private val pokemonStatBar2: ProgressBar by lazy { findViewById(R.id.horizontal_loading_bar_2) }
    private val pokemonStatBar3: ProgressBar by lazy { findViewById(R.id.horizontal_loading_bar_3) }
    private val pokemonStatBar4: ProgressBar by lazy { findViewById(R.id.horizontal_loading_bar_4) }
    private val pokemonStatBar5: ProgressBar by lazy { findViewById(R.id.horizontal_loading_bar_5) }
    private val pokemonStatBar6: ProgressBar by lazy { findViewById(R.id.horizontal_loading_bar_6) }

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon)
        pokemonId = intent.getIntExtra(EXTRA_POKEMON_ID, -1)
        if (pokemonId == -1) {
            throw IllegalStateException("Pokemon ID is not found")
        }
        pokemonViewModel.getInformation(pokemonId)

        compositeDisposable.add(pokemonViewModel.pokemonViewStateObservable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { pokemonViewState -> setFragmentMembers(pokemonViewState) },
                { error -> Log.e(TAG, error.localizedMessage ?: "Error") }
            ))
    }

    override fun setNewViewState(pokemonViewState: PokemonViewState) {
        setFragmentMembers(pokemonViewState)
    }

    private fun setFragmentMembers(pokemonViewState: PokemonViewState) {
        var actionBar: androidx.appcompat.app.ActionBar? = supportActionBar

        if (actionBar != null) {
            actionBar.title = pokemonViewState.pokemonName
        }

        Picasso.get()
            .load(pokemonViewState.pokemonImageURL+pokemonId+pokemonViewState.pokemonImageDotPNG)
            .into(pokemonEnhancedImageView)

        pokemonType1.text = pokemonViewState.pokemonStatType1String

        pokemonType2.text = pokemonViewState.pokemonStatType2String

        pokemonStat1Name.text = pokemonViewState.pokemonStat1String
        pokemonStat2Name.text = pokemonViewState.pokemonStat2String
        pokemonStat3Name.text = pokemonViewState.pokemonStat3String
        pokemonStat4Name.text = pokemonViewState.pokemonStat4String
        pokemonStat5Name.text = pokemonViewState.pokemonStat5String
        pokemonStat6Name.text = pokemonViewState.pokemonStat6String

        pokemonStat1Value.text = pokemonViewState.pokemonStat1.toString()
        pokemonStatBar1.progress = pokemonViewState.pokemonStat1

        pokemonStat2Value.text = pokemonViewState.pokemonStat2.toString()
        pokemonStatBar2.progress = pokemonViewState.pokemonStat2

        pokemonStat3Value.text = pokemonViewState.pokemonStat3.toString()
        pokemonStatBar3.progress = pokemonViewState.pokemonStat3

        pokemonStat4Value.text = pokemonViewState.pokemonStat4.toString()
        pokemonStatBar4.progress = pokemonViewState.pokemonStat4

        pokemonStat5Value.text = pokemonViewState.pokemonStat5.toString()
        pokemonStatBar5.progress = pokemonViewState.pokemonStat5

        pokemonStat6Value.text = pokemonViewState.pokemonStat6.toString()
        pokemonStatBar6.progress = pokemonViewState.pokemonStat6
    }
}

