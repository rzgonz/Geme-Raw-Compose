package com.example.rawg.presentation.home.favorite

import android.widget.Toast
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.test.core.app.ActivityScenario.launch
import com.common.common.ext.logD
import com.example.rawg.navigation.Screen
import com.example.rawg.ui.theme.componen.CardGameFavorite
import kotlinx.coroutines.coroutineScope
import org.koin.androidx.compose.koinViewModel

@Composable
fun FavoriteScreem(
    navController: NavController,
    favoriteViewModel: FavoriteViewModel = koinViewModel()
) {
    val mContext = LocalContext.current
    favoriteViewModel.getListGameFavorite()
    val gameListItems = favoriteViewModel.state.collectAsState().value.detailGame.invoke().orEmpty()
    LazyColumn {
        items(gameListItems.size) { index ->
            CardGameFavorite(gameDetailDto = gameListItems[index], onCardSelected = { id ->
                logD<Screen.Detail>("ayam $id")
                navController.navigate(Screen.Detail.createRoute(id)) {
                    popUpTo(navController.graph.findStartDestination().id)
                    launchSingleTop = true
                }
            }, onDeleteClick = {
                Toast.makeText(mContext,"Game ${it.name} remove from favorite",Toast.LENGTH_LONG).show()
                favoriteViewModel.deleteFromFavorite(it)
            }
            )
        }
    }
}
