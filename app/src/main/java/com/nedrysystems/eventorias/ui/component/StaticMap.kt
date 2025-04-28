package com.nedrysystems.eventorias.ui.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.nedrysystems.eventorias.BuildConfig


@Composable
fun StaticMap(
    latitude: Double,
    longitude: Double,
    modifier: Modifier = Modifier,
    zoom: Int = 17,
    sizePx: Pair<Int, Int> = 800 to 400,
    heightDp: Dp = 100.dp
) {

    val apiKey = BuildConfig.GOOGLE_API_KEY
    val (widthPx, heightPx) = sizePx
    val url = buildString {
        append("https://maps.googleapis.com/maps/api/staticmap")
        append("?center=$latitude,$longitude")
        append("&zoom=$zoom")
        append("&size=${widthPx}x${heightPx}")
        append("&markers=color:red%7C$latitude,$longitude")
        append("&key=$apiKey")
    }

    AsyncImage(
        model = url,
        contentDescription = "Preview carte statique",
        contentScale = ContentScale.Crop,
        modifier = modifier
            .fillMaxWidth()
            .height(heightDp)
            .clip(RoundedCornerShape(16.dp))
            .border(
                width = 1.dp,
                color = Color.Gray,
                shape = RoundedCornerShape(16.dp)
            )
    )
}


@Preview
@Composable
fun PreviewStaticMap() {
    StaticMap(
        longitude = -0.43796160000000006,
        latitude = 45.448140099999996
    )
}


