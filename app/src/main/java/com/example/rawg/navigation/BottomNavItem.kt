package com.example.rawg.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(var title:String, var icon: ImageVector, var route:String){

    object ListGame : BottomNavItem("Home", Icons.Default.Home,"listgame")
    object Favorite: BottomNavItem("Favorite",Icons.Default.Favorite,"favorite")
}