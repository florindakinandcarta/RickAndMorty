package com.example.rickandmorty.ui.comoon

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.rickandmorty.ui.theme.RickAction

private val defaultModifier = Modifier
    .fillMaxSize()
    .padding(all = 128.dp)

@Composable
fun LoadingState() {
    CircularProgressIndicator(
        modifier = defaultModifier,
        color = RickAction
    )
}