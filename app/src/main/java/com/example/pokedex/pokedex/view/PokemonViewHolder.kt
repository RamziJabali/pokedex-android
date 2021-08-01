package com.example.pokedex.pokedex.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.R
import com.squareup.picasso.Picasso

class PokemonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val pokemonTextView: TextView = itemView.findViewById(R.id.pokemonName)
    private val pokemonImageView: ImageView = itemView.findViewById(R.id.pokemonImage)

    companion object {
        fun new(context: Context, parent: ViewGroup?): PokemonViewHolder =
            PokemonViewHolder(
                LayoutInflater.from(context)
                    .inflate(
                        R.layout.item_view,
                        parent,
                        false
                    )
            )
    }

    fun bind(gridProperties: GridProperties) {
        pokemonTextView.text = gridProperties.itemText
        //pokemonImageView.setImageResource(gridProperties.itemImage)
        itemView.setBackgroundResource(gridProperties.itemBackgroundColor)
        Picasso.get().load("https://pokeres.bastionbot.org/images/pokemon/${gridProperties.itemOrderNumber}.png").into(pokemonImageView)
        itemView.invalidate()
    }


}
