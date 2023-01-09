package com.example.rawg.presentation.detail

import android.widget.TextView
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.common.common.Fail
import com.common.common.Loading
import com.common.common.Success
import com.common.common.Uninitialized
import com.example.rawg.R
import com.example.rawg.domain.dto.GameDetailDto
import com.example.rawg.ui.theme.SampleComposeTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun DetailScreen(
    navController: NavController,
    gameId: String,
    detailViewModel: DetailViewModel = koinViewModel(),
) {
    val firstLoad = remember { mutableStateOf(true) }
    if (firstLoad.value) {
        firstLoad.value = false
        detailViewModel.getDetailGame(gameId)
    }
    val viewState by detailViewModel.state.collectAsState()
    val mContext = LocalContext.current
    Scaffold(
        modifier = Modifier,
        topBar = {
            TopAppBar(
                title = { Text(text = "Detail") },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigateUp()
                    }) {
                        Icon(Icons.Filled.ArrowBack, "backIcon")
                    }
                },
                actions = {
                    if (viewState.detailGame.complete) {
                        IconButton(onClick = {
                            viewState.detailGame.invoke()
                                ?.let {
                                    Toast.makeText(
                                        mContext, "Game ${it.name} save to favorite",
                                        Toast.LENGTH_LONG
                                    ).show()
                                    detailViewModel.saveToFavorite(it)
                                }
                        }) {
                            Icon(Icons.Default.Favorite, contentDescription = "saveIcon")
                        }
                    }
                }
            )
        },
        content = {
            when (val data = viewState.detailGame) {
                is Fail -> {
                    Text(text = "${data.error.message}")
                }
                is Loading -> {
                    DetailLoadingView(it)
                }
                is Success -> {
                    DetailContent(gameDetailDto = data.invoke(), paddingValues = it)
                }
                Uninitialized -> {
                    Text(text = "Uninitialized")
                }
            }
        }
    )
}

@Composable
private fun HtmlText(html: String, modifier: Modifier = Modifier) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            TextView(context).apply {

            }
        },
        update = { it.text = HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_COMPACT) }
    )
}

@Composable
private fun DetailLoadingView(paddingValues: PaddingValues) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        CircularProgressIndicator(modifier = Modifier.padding(16.dp))
    }
}


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun DetailContent(
    gameDetailDto: GameDetailDto,
    paddingValues: PaddingValues,
    isPreview: Boolean = false
) {
    LazyColumn(content = {
        item {
            if (isPreview) {
                Image(
                    painter = painterResource(id = R.drawable.screen),
                    contentDescription = "",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.fillMaxWidth(),
                )
            } else {
                GlideImage(
                    model = gameDetailDto.imagePreview, contentDescription = "",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(12.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = gameDetailDto.publishers,
                    color = Color.Gray,
                    fontSize = 12.sp
                )
                Text(
                    text = gameDetailDto.name,
                    color = Color.Black,
                    fontSize = 18.sp
                )
                Text(
                    text = "Release Data ${gameDetailDto.releaseDate}",
                    color = Color.Gray,
                    fontSize = 12.sp
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = android.R.drawable.star_big_on),
                        contentDescription = "",
                        modifier = Modifier.width(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = gameDetailDto.rating.toString(),
                        color = Color.Gray,
                        fontSize = 10.sp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Image(
                        painter = painterResource(id = R.drawable.ic_game_stick),
                        contentDescription = "",
                        modifier = Modifier.width(14.dp),
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = gameDetailDto.players.toString(),
                        color = Color.Gray,
                        fontSize = 10.sp
                    )
                }
                Spacer(modifier = Modifier.height(18.dp))
                HtmlText(
                    html = gameDetailDto.description,
                )
            }
        }


    })
}

@Preview(name = "Game Detail Preview", showBackground = true)
@Composable
private fun GameDetailPreview() {
    SampleComposeTheme {
        DetailContent(
            isPreview = true, gameDetailDto =
            GameDetailDto(
                imagePreview = "",
                publishers = "rokcet publivkc",
                name = "Nama game ja",
                releaseDate = "2020/10/12", rating = 2.5,
                players = 6666,
                description = "loperapkdpajdp jpd apsdkpa skdpaskdp kaspdkapskdpaskdpakspdkasdk pdk"
            ), paddingValues = PaddingValues(0.dp)
        )
    }
}


