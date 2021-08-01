package com.example.pokedex.pokedex.view

import com.example.pokedex.pokedex.viewmodel.ViewState

interface ViewListener {
    fun setNewViewState(viewState: ViewState)
}