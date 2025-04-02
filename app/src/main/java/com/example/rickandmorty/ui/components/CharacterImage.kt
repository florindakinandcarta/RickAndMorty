package com.example.rickandmorty.ui.components

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage

@Composable
fun CharacterImage(imageUrl: String) {
    SubcomposeAsyncImage(
        model = imageUrl,
        contentDescription = "Character Image",
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clip(RoundedCornerShape(12.dp)),
        loading = {
            LoadingState()
        },
        onError = { println("Coil image load failed: ${it.result.throwable}") }
    )
}