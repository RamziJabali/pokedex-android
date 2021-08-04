package com.example.pokedex.pokemon.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
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

    private lateinit var pokemonEnhancedImageView: ImageView

    private lateinit var pokemonType1: TextView
    private lateinit var pokemonType2: TextView

    private lateinit var pokemonStat1Name: TextView
    private lateinit var pokemonStat2Name: TextView
    private lateinit var pokemonStat3Name: TextView
    private lateinit var pokemonStat4Name: TextView
    private lateinit var pokemonStat5Name: TextView
    private lateinit var pokemonStat6Name: TextView

    private lateinit var pokemonStat1Value: TextView
    private lateinit var pokemonStat2Value: TextView
    private lateinit var pokemonStat3Value: TextView
    private lateinit var pokemonStat4Value: TextView
    private lateinit var pokemonStat5Value: TextView
    private lateinit var pokemonStat6Value: TextView

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon)
        setupFragmentMembers()
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

    private fun setupFragmentMembers() {
        pokemonType1 = findViewById(R.id.pokemonTypeOneTextView)
        pokemonType2 = findViewById(R.id.pokemonTypeTwoTextView)
        pokemonStat1Name = findViewById(R.id.statOne)
        pokemonStat2Name = findViewById(R.id.statTwo)
        pokemonStat3Name = findViewById(R.id.statThree)
        pokemonStat4Name = findViewById(R.id.statFour)
        pokemonStat5Name = findViewById(R.id.statFive)
        pokemonStat6Name = findViewById(R.id.statSix)

        pokemonStat1Value = findViewById(R.id.statOneValue)
        pokemonStat2Value = findViewById(R.id.statTwoValue)
        pokemonStat3Value = findViewById(R.id.statThreeValue)
        pokemonStat4Value = findViewById(R.id.statFourValue)
        pokemonStat5Value = findViewById(R.id.statFiveValue)
        pokemonStat6Value = findViewById(R.id.statSixValue)

        pokemonEnhancedImageView = findViewById(R.id.pokemonImageEnhanced)
    }

    private fun setFragmentMembers(pokemonViewState: PokemonViewState) {
        Picasso.get()
                .load("https://pokeres.bastionbot.org/images/pokemon/${pokemonId}.png")
                .into(pokemonEnhancedImageView)
        pokemonType1.text = pokemonViewState.pokemonType[0].pokemonSpecificType.pokemonTypeName

        if (pokemonViewState.pokemonType.size > 1) {
            pokemonType2.text = pokemonViewState.pokemonType[1].pokemonSpecificType.pokemonTypeName
        }
        pokemonStat1Name.text = pokemonViewState.pokemonStats[0].pokemonStatType.statTypeName
        pokemonStat2Name.text = pokemonViewState.pokemonStats[1].pokemonStatType.statTypeName
        pokemonStat3Name.text = pokemonViewState.pokemonStats[2].pokemonStatType.statTypeName
        pokemonStat4Name.text = pokemonViewState.pokemonStats[3].pokemonStatType.statTypeName
        pokemonStat5Name.text = pokemonViewState.pokemonStats[4].pokemonStatType.statTypeName
        pokemonStat6Name.text = pokemonViewState.pokemonStats[5].pokemonStatType.statTypeName
        pokemonStat1Value.text = pokemonViewState.pokemonStats[0].pokemonBaseStat.toString()
        pokemonStat2Value.text = pokemonViewState.pokemonStats[1].pokemonBaseStat.toString()
        pokemonStat3Value.text = pokemonViewState.pokemonStats[2].pokemonBaseStat.toString()
        pokemonStat4Value.text = pokemonViewState.pokemonStats[3].pokemonBaseStat.toString()
        pokemonStat5Value.text = pokemonViewState.pokemonStats[4].pokemonBaseStat.toString()
        pokemonStat6Value.text = pokemonViewState.pokemonStats[5].pokemonBaseStat.toString()
    }
}

