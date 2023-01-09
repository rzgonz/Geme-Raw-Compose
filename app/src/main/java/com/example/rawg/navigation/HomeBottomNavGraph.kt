package com.example.rawg.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.common.common.ext.logD
import com.example.rawg.presentation.detail.DetailScreen
import com.example.rawg.presentation.home.favorite.FavoriteScreem
import com.example.rawg.presentation.home.listGame.ListGameScreen


@Composable
fun HomeBottomNavGraph(navController: NavHostController, paddingValues: PaddingValues) {
    NavHost(
        navController = navController,
        route = "home_graph",
        startDestination = BottomNavItem.ListGame.route
    ) {
        composable(route = BottomNavItem.ListGame.route) {
            ListGameScreen(navController = navController)
        }
        composable(route = BottomNavItem.Favorite.route) {
            FavoriteScreem(navController = navController)
        }
        detailsNavGraph(navController = navController)
    }
}

fun NavGraphBuilder.detailsNavGraph(navController: NavHostController) {
    navigation(
        route = "detail_graph",
        startDestination = Screen.Detail.route
    ) {
        composable(route = Screen.Detail.route) { backStackEntry ->
            val gameId =  backStackEntry.arguments?.getString("gameId")
            requireNotNull(gameId) { "gameId parameter wasn't found. Please make sure it's set!" }
            logD<Screen.Home>("DETAIL NAV")
            DetailScreen(navController,gameId)
        }
    }
}