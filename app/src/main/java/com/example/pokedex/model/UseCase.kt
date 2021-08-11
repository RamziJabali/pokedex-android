package com.example.pokedex.model

import com.example.pokedex.network.Pokedex.BasePokemon
import com.example.pokedex.network.Pokemon.Pokemon
import io.reactivex.Observable
import java.util.*
import kotlin.collections.ArrayList

class UseCase(private val pokedexRepo: PokedexRepo) {
    fun getPokemon(pokemonId: Int): Observable<Pokemon> = pokedexRepo.getPokemon(pokemonId)
    fun getPokedex(limit: Int, offset: Int): Observable<BasePokedex> =
        pokedexRepo.getPokedex(limit, offset)
            .map { pokedex ->
                BasePokedex(
                    pokemonName = pokedexPokemonNameArrayElementFormatting(pokedex.pokedex),
                    pokemonIdString = pokedexPokemonIdStringArrayElementFormatting(pokedex.pokedex),
                    pokemonId = pokedexPokemonIdArrayElementFormatting(pokedex.pokedex)
                )
            }


    private fun pokedexPokemonNameArrayElementFormatting(pokedexArray: Array<BasePokemon>): ArrayList<String> {
        var formattedArrayList = ArrayList<String>()
        for (index in pokedexArray.indices) {
            formattedArrayList.add(pokedexArray[index].pokemonName.capitalize(Locale.getDefault()))
        }
        return formattedArrayList
    }

    private fun pokedexPokemonIdStringArrayElementFormatting(pokedexArray: Array<BasePokemon>): ArrayList<String> {
        var formattedArrayList = ArrayList<String>()
        for (index in pokedexArray.indices) {
            formattedArrayList.add(getIdFromURL(pokedexArray[index].pokemonURL))
        }
        return formattedArrayList
    }

    private fun pokedexPokemonIdArrayElementFormatting(pokedexArray: Array<BasePokemon>): ArrayList<Int> {
        var formattedArrayList = ArrayList<Int>()
        for (index in pokedexArray.indices) {
            formattedArrayList.add(getIdFromURL(pokedexArray[index].pokemonURL).toInt())
        }
        return formattedArrayList
    }

    private fun getIdFromURL(url: String): String {
        var fixedId = ""
        var index = url.length - 2
        while (url[index].isDigit()) {
            fixedId = "${url[index]}$fixedId"
            index -= 1
        }
        return fixedId.padStart(3,'0')
    }
}