package com.example.pokedex

import androidx.core.content.ContextCompat.startActivity
import com.example.pokedex.pokedex.view.MainActivity
import com.example.pokedex.pokemon.view.PokemonActivity
import io.mockk.mockk
import org.junit.Assert.assertTrue
import org.junit.Before

class PokemonTest {

    private val mainActivity: MainActivity by lazy { mockk(relaxed = true) }
    private val pokemonActivity: PokemonActivity by lazy { mockk(relaxed = true) }

    @Before
    fun setup(){
        pokemonActivity.pokemonViewModel.getInformation(mainActivity.viewState.gridProperties[0].itemOrderNumber)
    }

    fun pokemonTypeTest(){
        assertTrue(pokemonActivity.pokemonType1.text.isNotEmpty())
    }
}