package com.example.pokedex.view

import com.example.pokedex.viewmodelpokedex.ViewState

interface ViewListener {
    fun setNewViewState(viewState: ViewState)
}