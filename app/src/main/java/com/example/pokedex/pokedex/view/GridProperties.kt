package com.example.pokedex.pokedex.view

import androidx.annotation.ColorRes
import com.example.pokedex.R

data class GridProperties(
    var itemText: String = "",
    @ColorRes var itemBackgroundColor: Int = R.color.design_default_color_primary,
    var itemImage: Int = R.drawable.ic_launcher_background,
    var itemOrderNumber: Int = -1,
    var itemOrderNumberString: String = ""

)