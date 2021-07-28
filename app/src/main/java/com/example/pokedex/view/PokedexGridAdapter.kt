package com.example.pokedex.view

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter

class PokedexGridAdapter : BaseAdapter() {

    private var gridProperties: Array<GridProperties> = emptyArray()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val context = parent!!.context

        val viewHolder: PokemonViewHolder =
            PokemonViewHolder.new(context, parent)

        viewHolder.bind(getItem(position))

        return viewHolder.itemView
    }

    override fun getCount(): Int = gridProperties.size

    override fun getItem(position: Int): GridProperties = gridProperties[position]

    override fun getItemId(position: Int): Long = position.toLong()

    fun setGridItems(items: ArrayList<GridProperties>) {
        gridProperties = items.toTypedArray()
        notifyDataSetChanged()
    }
}