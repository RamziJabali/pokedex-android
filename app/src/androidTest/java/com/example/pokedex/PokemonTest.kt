package com.example.pokedex

import androidx.core.content.ContextCompat.startActivity
import com.example.pokedex.pokedex.view.MainActivity
import com.example.pokedex.pokemon.view.PokemonActivity
import io.mockk.mockk
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class PokemonTest {

    private val mainActivity: MainActivity by lazy { mockk(relaxed = true) }
    private val pokemonActivity: PokemonActivity by lazy { mockk(relaxed = true) }

    @Before
    fun setup() {
        pokemonActivity.pokemonViewModel.getInformation(mainActivity.viewState.gridProperties[0].itemOrderNumber)
    }

    @Test
    fun pokemonTypeTest() {
        assertTrue(pokemonActivity.pokemonType1.text.isNotEmpty())
    }

    @Test
    fun pokemonStat1NameTest() {
        assertTrue(pokemonActivity.pokemonStat1Name.text.equals("HP"))
    }

    @Test
    fun pokemonStat2NameTest() {
        assertTrue(pokemonActivity.pokemonStat2Name.text.equals("ATTACK"))
    }

    @Test
    fun pokemonStat3NameTest() {
        assertTrue(pokemonActivity.pokemonStat3Name.text.equals("DEFENSE"))
    }

    @Test
    fun pokemonStat4NameTest() {
        assertTrue(pokemonActivity.pokemonStat4Name.text.equals("SPECIAL-ATTACK"))
    }

    @Test
    fun pokemonStat5NameTest() {
        assertTrue(pokemonActivity.pokemonStat5Name.text.equals("SPECIAL-DEFENSE"))
    }

    @Test
    fun pokemonStat6NameTest() {
        assertTrue(pokemonActivity.pokemonStat6Name.text.equals("SPEED"))
    }

    @Test
    fun pokemonStat1ValueTest() {
        assertTrue(pokemonActivity.pokemonStat1Value.text.isNotBlank())
    }

    @Test
    fun pokemonStat2ValueTest() {
        assertTrue(pokemonActivity.pokemonStat2Value.text.isNotBlank())
    }

    @Test
    fun pokemonStat3ValueTest() {
        assertTrue(pokemonActivity.pokemonStat3Value.text.isNotBlank())
    }

    @Test
    fun pokemonStat4ValueTest() {
        assertTrue(pokemonActivity.pokemonStat4Value.text.isNotBlank())
    }

    @Test
    fun pokemonStat5ValueTest() {
        assertTrue(pokemonActivity.pokemonStat5Value.text.isNotBlank())
    }
    @Test
    fun pokemonStat6ValueTest() {
        assertTrue(pokemonActivity.pokemonStat6Value.text.isNotBlank())
    }
    @Test
    fun pokemonImageTest() {
        assertTrue(pokemonActivity.pokemonEnhancedImageView.drawable != null)
    }
}