package com.birdeveloper.composebottomnavigationbar.data

import androidx.annotation.DrawableRes

data class BottomNavigationItem(
    val routeId: String,
    val name: String,
    @DrawableRes val iconId: Int
)