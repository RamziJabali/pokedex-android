package com.example.pokedex.pokemon.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.pokedex.R
import com.example.pokedex.pokedex.viewmodel.ViewState
import com.squareup.picasso.Picasso

class PokemonFragment : Fragment() {

    private lateinit var pokemonEnhancedImageView: ImageView
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


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_pokemon, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupFragmentMembers(requireView())
    }

    private fun setupFragmentMembers(view: View) {
        pokemonType1 = view.findViewById(R.id.pokemonTypeOneTextView)
        pokemonType2 = view.findViewById(R.id.pokemonTypeTwoTextView)
        pokemonStat1Name = view.findViewById(R.id.statOne)
        pokemonStat2Name = view.findViewById(R.id.statTwo)
        pokemonStat3Name = view.findViewById(R.id.statThree)
        pokemonStat4Name = view.findViewById(R.id.statFour)
        pokemonStat1Value = view.findViewById(R.id.statOneValue)
        pokemonStat2Value = view.findViewById(R.id.statTwoValue)
        pokemonStat3Value = view.findViewById(R.id.statThreeValue)
        pokemonStat4Value = view.findViewById(R.id.statFourValue)
        pokemonEnhancedImageView = view.findViewById(R.id.pokemonImageEnhanced)
    }

    fun setFragmentMembers(viewState: ViewState, position: Int) {
        with(viewState) {
            Picasso.get()
                .load("https://pokeres.bastionbot.org/images/pokemon/${gridProperties[position].itemOrderNumber}.png")
                .into(pokemonEnhancedImageView)
            pokemonType1.text =
                pokemonArrayList[gridProperties[position].itemOrderNumber].pokemonType[0].pokemonSpecificType.pokemonTypeName
            if (pokemonArrayList[gridProperties[position].itemOrderNumber].pokemonType.size > 1) {
                pokemonType2.text =
                    pokemonArrayList[gridProperties[position].itemOrderNumber].pokemonType[1].pokemonSpecificType.pokemonTypeName
            }
            pokemonStat1Name.text =
                pokemonArrayList[gridProperties[position].itemOrderNumber].pokemonStats[0].pokemonStatType.statTypeName
            pokemonStat2Name.text =
                pokemonArrayList[gridProperties[position].itemOrderNumber].pokemonStats[1].pokemonStatType.statTypeName
            pokemonStat3Name.text =
                pokemonArrayList[gridProperties[position].itemOrderNumber].pokemonStats[2].pokemonStatType.statTypeName
            pokemonStat4Name.text =
                pokemonArrayList[gridProperties[position].itemOrderNumber].pokemonStats[3].pokemonStatType.statTypeName

            pokemonStat1Value.text =
                pokemonArrayList[gridProperties[position].itemOrderNumber].pokemonStats[0].pokemonBaseStat.toString()
            pokemonStat2Value.text =
                pokemonArrayList[gridProperties[position].itemOrderNumber].pokemonStats[1].pokemonBaseStat.toString()
            pokemonStat3Value.text =
                pokemonArrayList[gridProperties[position].itemOrderNumber].pokemonStats[2].pokemonBaseStat.toString()
            pokemonStat4Value.text =
                pokemonArrayList[gridProperties[position].itemOrderNumber].pokemonStats[3].pokemonBaseStat.toString()
        }
    }
}