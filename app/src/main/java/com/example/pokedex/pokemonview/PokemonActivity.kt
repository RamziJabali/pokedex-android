package com.example.pokedex.pokemonview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.example.pokedex.R
import com.example.pokedex.pokedexview.ViewListener
import com.example.pokedex.viewmodelpokedex.ViewState
import com.example.pokedex.viewmodelpokemon.PokemonViewState
import com.squareup.picasso.Picasso

class PokemonActivity : AppCompatActivity(), PokemonViewListener {

    private lateinit var pokemonImageView: ImageView
    private lateinit var pokemonType1: TextView
    private lateinit var pokemonType2: TextView
    private lateinit var pokemonStat1Name: TextView
    private lateinit var pokemonStat2Name: TextView
    private lateinit var pokemonStat3Name: TextView
    private lateinit var pokemonStat4Name: TextView
    private lateinit var pokemonStat1Value: TextView
    private lateinit var pokemonStat2Value: TextView
    private lateinit var pokemonStat3Value: TextView
    private lateinit var pokemonStat4Value: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon)
        setup()
    }

    private fun setup() {
        pokemonImageView = findViewById(R.id.pokemonImageEnhanced)
        pokemonType1 = findViewById(R.id.pokemonTypeOneTextView)
        pokemonType2 = findViewById(R.id.pokemonTypeTwoTextView)
        pokemonStat1Name = findViewById(R.id.statOne)
        pokemonStat2Name = findViewById(R.id.statTwo)
        pokemonStat3Name = findViewById(R.id.statThree)
        pokemonStat4Name = findViewById(R.id.statFour)
        pokemonStat1Value = findViewById(R.id.statOneValue)
        pokemonStat2Value = findViewById(R.id.statTwoValue)
        pokemonStat3Value = findViewById(R.id.statThreeValue)
        pokemonStat4Value = findViewById(R.id.statFourValue)
    }

    override fun setPokemonViewState(pokemonViewState: PokemonViewState) {
        pokemonType1.text = pokemonViewState.pokemonTypeName[0]
        if (pokemonViewState.pokemonTypeName.size > 1) {
            pokemonType2.text = pokemonViewState.pokemonTypeName[1]
        }
        pokemonStat1Name.text = pokemonViewState.pokemonBaseStatName[0]
        pokemonStat2Name.text = pokemonViewState.pokemonBaseStatName[1]
        pokemonStat3Name.text = pokemonViewState.pokemonBaseStatName[2]
        pokemonStat4Name.text = pokemonViewState.pokemonBaseStatName[3]
        pokemonStat1Value.text = pokemonViewState.pokemonBaseStat[0].toString()
        pokemonStat2Value.text = pokemonViewState.pokemonBaseStat[1].toString()
        pokemonStat3Value.text = pokemonViewState.pokemonBaseStat[2].toString()
        pokemonStat4Value.text = pokemonViewState.pokemonBaseStat[3].toString()
        Picasso.get()
            .load("https://pokeres.bastionbot.org/images/pokemon/${pokemonViewState.pokemonOrderNumber}.png")
            .into(pokemonImageView)
    }

}