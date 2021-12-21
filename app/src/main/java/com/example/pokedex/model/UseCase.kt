package com.example.pokedex.model

import com.example.pokedex.network.Pokedex.BasePokemon
import com.example.pokedex.network.Pokemon.PokemonType
import io.reactivex.Observable
import io.reactivex.Single
import java.util.*
import kotlin.collections.ArrayList

class UseCase(private val pokedexRepo: PokedexRepo) {
    fun getPokemon(pokemonId: Int): Observable<ModifiedPokemon> =
        pokedexRepo.getPokemon(pokemonId).map { pokemon ->
            ModifiedPokemon(
                pokemonImageURL = getCompletedPokemonImageURL(pokemonId.toString()),
                pokemonName = pokemon.pokemonName.capitalize(),
                pokemonStat1 = pokemon.pokemonStats[0].pokemonBaseStat,
                pokemonStat2 = pokemon.pokemonStats[1].pokemonBaseStat,
                pokemonStat3 = pokemon.pokemonStats[2].pokemonBaseStat,
                pokemonStat4 = pokemon.pokemonStats[3].pokemonBaseStat,
                pokemonStat5 = pokemon.pokemonStats[4].pokemonBaseStat,
                pokemonStat6 = pokemon.pokemonStats[5].pokemonBaseStat,
                pokemonStat1String = pokemon.pokemonStats[0].pokemonStatType.statTypeName.toUpperCase(),
                pokemonStat2String = pokemon.pokemonStats[1].pokemonStatType.statTypeName.toUpperCase(),
                pokemonStat3String = pokemon.pokemonStats[2].pokemonStatType.statTypeName.toUpperCase(),
                pokemonStat4String = pokemon.pokemonStats[3].pokemonStatType.statTypeName.toUpperCase(),
                pokemonStat5String = pokemon.pokemonStats[4].pokemonStatType.statTypeName.toUpperCase(),
                pokemonStat6String = pokemon.pokemonStats[5].pokemonStatType.statTypeName.toUpperCase(),
                pokemonStatType1String = pokemon.pokemonTypes[0].pokemonSpecificType.pokemonTypeName.toUpperCase(),
                pokemonStatType2String = getSecondPokemonType(pokemon.pokemonTypes)
            )
        }

    private fun getSecondPokemonType(pokemonTypes: Array<PokemonType>): String {
        if (pokemonTypes.size > 1) {
            return pokemonTypes[1].pokemonSpecificType.pokemonTypeName.toUpperCase()
        }
        return ""
    }


    fun getPokedex(offset: Int, limit: Int): Single<BasePokedex> =
        pokedexRepo.getPokedex(offset, limit)
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
            index--
        }
        return fixedId.padStart(3, '0')
    }

    private fun getCompletedPokemonImageURL(id: String): String {
        return "https://assets.pokemon.com/assets/cms2/img/pokedex/full/${id.padStart(3, '0')}.png"
    }

}