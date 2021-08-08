package com.example.pokedex

import androidx.core.content.ContextCompat.startActivity
import com.example.pokedex.pokedex.view.MainActivity
import com.example.pokedex.pokemon.view.PokemonActivity
import io.mockk.mockk
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class PokedexTest {

    private lateinit var mainActivity: MainActivity

    @Before
    fun setup() {
        mainActivity = mockk(relaxed = true)
        mainActivity.viewModel.startApplication()
    }

    @Test
    fun pokemonNameTest() {
        val blockFromGrid = mainActivity.gridViewAdapter.getItem(0)
        assertTrue(blockFromGrid.itemText.isNotEmpty())
    }

    @Test
    fun pokemonIDNumberTest() {
        val blockFromGrid = mainActivity.gridViewAdapter.getItem(0)
        assertTrue(blockFromGrid.itemOrderNumber != -1)
    }

    @Test
    fun pokemonIDStringTest() {
        val blockFromGrid = mainActivity.gridViewAdapter.getItem(0)
        assertTrue(blockFromGrid.itemOrderNumberString.isNotEmpty())
    }
}