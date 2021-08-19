package com.example.pokedex.pokemon.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.transition.Explode
import android.util.Log
import android.view.Window
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AppCompatActivity
import com.example.pokedex.BuildConfig
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
        const val ANIMATION_TIME_LENGTH: Long = 500

        fun newInstance(context: Context, pokeId: Int): Intent {
            return Intent(context, PokemonActivity::class.java).apply {
                putExtra(EXTRA_POKEMON_ID, pokeId)
            }
        }

    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val pokemonViewModel: PokemonViewModel by viewModel()
    private var pokemonId: Int = -1

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val pokemonEnhancedImageView: ImageView by lazy { findViewById(R.id.pokemonImageEnhanced) }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val pokemonType1: TextView by lazy { findViewById(R.id.pokemonTypeOneTextView) }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val pokemonType2: TextView by lazy { findViewById(R.id.pokemonTypeTwoTextView) }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val pokemonStat1Name: TextView by lazy { findViewById(R.id.statOne) }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val pokemonStat2Name: TextView by lazy { findViewById(R.id.statTwo) }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val pokemonStat3Name: TextView by lazy { findViewById(R.id.statThree) }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val pokemonStat4Name: TextView by lazy { findViewById(R.id.statFour) }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val pokemonStat5Name: TextView by lazy { findViewById(R.id.statFive) }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val pokemonStat6Name: TextView by lazy { findViewById(R.id.statSix) }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val pokemonStat1Value: TextView by lazy { findViewById(R.id.statOneValue) }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val pokemonStat2Value: TextView by lazy { findViewById(R.id.statTwoValue) }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val pokemonStat3Value: TextView by lazy { findViewById(R.id.statThreeValue) }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val pokemonStat4Value: TextView by lazy { findViewById(R.id.statFourValue) }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val pokemonStat5Value: TextView by lazy { findViewById(R.id.statFiveValue) }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val pokemonStat6Value: TextView by lazy { findViewById(R.id.statSixValue) }

    private val pokemonStatBar1: ProgressBar by lazy { findViewById(R.id.horizontal_loading_bar_1) }
    private val pokemonStatBar2: ProgressBar by lazy { findViewById(R.id.horizontal_loading_bar_2) }
    private val pokemonStatBar3: ProgressBar by lazy { findViewById(R.id.horizontal_loading_bar_3) }
    private val pokemonStatBar4: ProgressBar by lazy { findViewById(R.id.horizontal_loading_bar_4) }
    private val pokemonStatBar5: ProgressBar by lazy { findViewById(R.id.horizontal_loading_bar_5) }
    private val pokemonStatBar6: ProgressBar by lazy { findViewById(R.id.horizontal_loading_bar_6) }

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(window) {
            requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
            enterTransition = Explode()
            exitTransition = Explode()
            enterTransition.duration = ANIMATION_TIME_LENGTH
            exitTransition.duration = ANIMATION_TIME_LENGTH
        }

        setContentView(R.layout.activity_pokemon)

        pokemonId = intent.getIntExtra(EXTRA_POKEMON_ID, -1)
        if (pokemonId == -1) {
            throw IllegalStateException("Pokemon ID is not found")
        }
        pokemonViewModel.getInformation(pokemonId)
        monitoringViewState()
    }

    private fun monitoringViewState() {
        compositeDisposable.add(pokemonViewModel.pokemonViewStateObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { pokemonViewState -> setPokemonViewElements(pokemonViewState) },
                        { error ->
                            if (BuildConfig.DEBUG) Log.d(TAG, error.localizedMessage ?: "Error")
                        }
                ))
    }

    override fun setNewViewState(pokemonViewState: PokemonViewState) {
        setPokemonViewElements(pokemonViewState)
    }

    private fun setPokemonViewElements(pokemonViewState: PokemonViewState) {
        var actionBar: androidx.appcompat.app.ActionBar? = supportActionBar

        if (actionBar != null) {
            actionBar.title = pokemonViewState.pokemonName
        }

        Picasso.get()
                .load(pokemonViewState.pokemonImageURL)
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

