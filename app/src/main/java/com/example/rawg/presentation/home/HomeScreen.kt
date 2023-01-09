package com.example.rawg.presentation.home

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.rawg.R
import com.example.rawg.navigation.BottomNavItem
import com.example.rawg.navigation.HomeBottomNavGraph
import com.example.rawg.ui.theme.DarkBlue

@Composable
fun HomeScreen(navController: NavHostController = rememberNavController()) {
    val indexNav = remember {
        mutableStateOf(BottomNavItem.ListGame.route)
    }
    var showBottomBar by rememberSaveable { mutableStateOf(true) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    showBottomBar = when (navBackStackEntry?.destination?.route) {
        BottomNavItem.ListGame.route -> true // on this screen bottom bar should be hidden
        BottomNavItem.Favorite.route -> true // here too
        else -> false // in all other cases show bottom bar
    }
    Scaffold(
        topBar = {
            if (showBottomBar) {
                TopAppBar(
                    title = {
                        Text(
                            text = stringResource(
                                id = if (BottomNavItem.ListGame.route == indexNav.value)
                                    R.string.home_label_header_title
                                else R.string.favorite_label_header_title
                            ),
                            color = Color.White
                        )
                    },
                    elevation = 0.dp,
                    backgroundColor = DarkBlue
                )
            }
        },
        bottomBar = {
            if (showBottomBar) {
                BottomHomeNavigation(navController, indexNav)
            }
        }
    ) {
        HomeBottomNavGraph(navController = navController, it)
    }

}


@Composable
fun BottomHomeNavigation(navController: NavController, indexNav: MutableState<String>) {
    val screens = listOf(
        BottomNavItem.ListGame,
        BottomNavItem.Favorite,
    )

    BottomNavigation {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        val bottomBarDestination = screens.any { it.route == currentDestination?.route }
        if (bottomBarDestination) {
            screens.forEach { item ->
                BottomNavigationItem(
                    icon = { Icon(imageVector = item.icon, contentDescription = item.title) },
                    label = { Text(text = item.title) },
                    selectedContentColor = Color.White,
                    unselectedContentColor = Color.White.copy(0.4f),
                    alwaysShowLabel = true,
                    selected = currentDestination?.hierarchy?.any {
                        it.route == item.route
                    } == true,
                    onClick = {
                        indexNav.value = item.route
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.findStartDestination().id)
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    }
}

