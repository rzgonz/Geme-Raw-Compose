package com.example.rawg.ui.theme.componen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.rawg.R
import com.example.rawg.domain.dto.GameDto
import com.example.rawg.ui.theme.SampleComposeTheme

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CardGame(
    gameDto: GameDto,
    onCardSelected: (Int) -> Unit,
    isPreview: Boolean = false
) {
    Row(modifier = Modifier
        .padding(horizontal = 16.dp, vertical = 5.dp)
        .fillMaxWidth()
        .clickable {
            onCardSelected.invoke(gameDto.id)
        }) {
        if (isPreview) {
            Image(
                painter = painterResource(id = R.drawable.screen),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(120.dp)
                    .height(90.dp)
                    .clip(RoundedCornerShape(8.dp)),
            )
        } else {
            GlideImage(
                model = gameDto.backgroundImage, contentDescription = "",
                modifier = Modifier
                    .width(120.dp)
                    .height(90.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(
                text = gameDto.name, color =
                Color.Black, fontSize = 18.sp
            )
            Text(
                text = "Release date ${gameDto.released}",
                color = Color.Gray, fontSize = 12.sp
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = android.R.drawable.star_big_on),
                    contentDescription = "",
                    modifier = Modifier.width(14.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = gameDto.rating.toString(),
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }

        }
    }
}

@Preview(name = "item Card game", showBackground = true)
@Composable
private fun CardGamePreview() {
    SampleComposeTheme {
        CardGame(isPreview = true, gameDto =
        GameDto(id = 1, "coba lg", released = "2020/10/12", rating = 2.5),
            onCardSelected = {})
    }
}