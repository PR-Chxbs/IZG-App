package com.prince.izg.ui.components.shared

import androidx.annotation.DrawableRes
import com.prince.izg.navigation.Screen

data class BottomNavItem(
    val route: String,
    val label: String,
    @DrawableRes val iconRes: Int
)
