package com.example.rawg.navigation


sealed class Screen(val route:String) {
    object Home: Screen("home")
    object Detail: Screen("detail/{gameId}"){
        fun createRoute(gameId: Int) = "detail/${gameId}"
    }
}