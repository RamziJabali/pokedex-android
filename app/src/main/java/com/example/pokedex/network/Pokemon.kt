package com.example.pokedex.network

import android.os.Parcel
import android.os.Parcelable
import androidx.versionedparcelable.ParcelField
import com.google.gson.annotations.SerializedName

data class Pokemon(
    @SerializedName("name")
    val pokemonName: String,
    @SerializedName("order")
    val pokemonOrderNumber: Int,
    @SerializedName("stats")
    val pokemonStats: Array<PokemonStats>,
    @SerializedName("types")
    val pokemonTypes: Array<PokemonType>
) 