package com.example.pokedex.pokedex.view

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil

class PokedexAdapter(
    private val onPokeClick: (Int) -> Unit
) :
    PagingDataAdapter<GridProperties, PokemonViewHolder>(PokemonComparator) {

    object PokemonComparator : DiffUtil.ItemCallback<GridProperties>() {
        override fun areItemsTheSame(oldItem: GridProperties, newItem: GridProperties): Boolean =
            oldItem.itemOrderNumber == newItem.itemOrderNumber

        override fun areContentsTheSame(oldItem: GridProperties, newItem: GridProperties): Boolean =
            oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder =
        PokemonViewHolder.new(parent.context, parent)

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.bind(getItem(position)!!, position, onPokeClick)
    }
}