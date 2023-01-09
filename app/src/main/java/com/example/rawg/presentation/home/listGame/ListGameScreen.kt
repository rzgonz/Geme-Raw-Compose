package com.example.rawg.presentation.home.listGame

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.rawg.navigation.Screen
import com.example.rawg.ui.theme.componen.CardGame
import org.koin.androidx.compose.koinViewModel


@Composable
fun ListGameScreen(
    mainViewModel: ListGameViewModel = koinViewModel(),
    navController: NavController,
) {
    val textState = remember { mutableStateOf(TextFieldValue("")) }
    Column {
        Spacer(modifier = Modifier.height(18.dp))
        SearchView(state = textState)
        Spacer(modifier = Modifier.height(18.dp))
        showListGame(navController = navController, query = textState.value)
    }

}

@Composable
fun showListGame(
    mainViewModel: ListGameViewModel = koinViewModel(),
    navController: NavController,
    query: TextFieldValue
) {
    val gameListItems = mainViewModel.listGameDto.collectAsLazyPagingItems()
    LazyColumn {
        items(gameListItems) { data ->
            data?.let {
                CardGame(gameDto = it, onCardSelected = { id ->
                    navController.navigate(Screen.Detail.createRoute(id)) {
                        popUpTo(navController.graph.findStartDestination().id)
                        launchSingleTop = true
                    }
                }
                )
            }
        }
        gameListItems.apply {

            when {
                loadState.refresh is LoadState.Loading -> {
                    //You can add modifier to manage load state when first time response page is loading
                    loading()
                }
                loadState.refresh is LoadState.NotLoading -> {
                    //You can add modifier to manage load state when first time response page is loading
                    if (gameListItems.itemCount == 0) {
                        item {
                            Row(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .fillMaxWidth()
                                    .background(Color.Red),
                                content = {
                                    Text(
                                        text = "No Data",
                                        modifier = Modifier
                                            .fillMaxHeight()
                                            .fillMaxWidth()
                                            .background(Color.Blue),
                                        fontSize = 14.sp,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            )

                        }
                    }
                }
                loadState.append is LoadState.Loading -> {
                    //You can add modifier to manage load state when next response page is loading
                    loading()
                }
                loadState.append is LoadState.Error -> {
                    //You can use modifier to show error message
                    error((loadState.append as LoadState.Error).error.message.orEmpty())
                }
            }
        }
    }
}


@Composable
fun SearchView(
    state: MutableState<TextFieldValue>,
    listGameViewModel: ListGameViewModel = koinViewModel()
) {
    Card(
        backgroundColor = Color.LightGray,
        modifier = Modifier.padding(horizontal = 18.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        TextField(
            value = state.value,
            onValueChange = { value ->
                state.value = value
                listGameViewModel.searchGame(value.text)
            },
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(color = Color.Black, fontSize = 18.sp),
            leadingIcon = {
                Icon(
                    Icons.Default.Search,
                    contentDescription = "",
                    modifier = Modifier
                        .padding(15.dp)
                        .size(24.dp)
                )
            },
            trailingIcon = {
                if (state.value != TextFieldValue("")) {
                    IconButton(
                        onClick = {
                            state.value =
                                TextFieldValue("") // Remove text from TextField when you press the 'X' icon
                        }
                    ) {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = "",
                            modifier = Modifier
                                .padding(15.dp)
                                .size(24.dp)
                        )
                    }
                }
            },
            placeholder = {
                Text("Search")
            },
            singleLine = true,
            shape = RectangleShape, // The TextFiled has rounded corners top left and right by default
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.Black,
                cursorColor = Color.Black,
                leadingIconColor = Color.Black,
                trailingIconColor = Color.Black,
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )
        )
    }

}


private fun LazyListScope.loading() {
    item {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
        }

    }
}

private fun LazyListScope.error(
    message: String
) {
    item {
        Text(
            text = message,
            style = MaterialTheme.typography.h6,
            color = MaterialTheme.colors.error
        )
    }
}