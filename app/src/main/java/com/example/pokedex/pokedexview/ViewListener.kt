package com.example.pokedex.pokedexview

import com.example.pokedex.viewmodelpokedex.ViewState

interface ViewListener {
    fun setNewViewState(viewState: ViewState)
}