package com.example.rickandmorty.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.rickandmorty.states.CharacterDetailsViewState
import com.example.rickandmorty.ui.comoon.CharacterImage
import com.example.rickandmorty.ui.comoon.DataPointComponent
import com.example.rickandmorty.ui.comoon.LoadingState
import com.example.rickandmorty.ui.comoon.SimpleToolbar
import com.example.rickandmorty.ui.components.CharacterDetailsNamePlateComponent
import com.example.rickandmorty.ui.theme.RickAction
import com.example.rickandmorty.viewmodels.CharacterDetailsViewModel

@Composable
fun CharacterDetailsScreen(
    characterId: Int,
    viewModel: CharacterDetailsViewModel = hiltViewModel(),
    onEpisodeClicked: (Int) -> Unit,
    onBackClicked: () -> Unit
) {
    val state by viewModel.stateFlow.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.fetchCharacter(characterId)
    }
    Column {
        SimpleToolbar(title = "Character details", onBackAction = { onBackClicked() })
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp)
        ) {
            when (val viewState = state) {
                is CharacterDetailsViewState.Error -> TODO()
                CharacterDetailsViewState.Loading -> item { LoadingState() }
                is CharacterDetailsViewState.Success -> {
                    item {
                        CharacterDetailsNamePlateComponent(
                            name = viewState.character.name,
                            status = viewState.character.status
                        )
                    }
                    item {
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    item {
                        CharacterImage(imageUrl = viewState.character.imageUrl)
                    }
                    items(viewState.characterDataPoint) {
                        Spacer(modifier = Modifier.height(32.dp))
                        DataPointComponent(dataPoint = it)
                    }
                    item { Spacer(modifier = Modifier.height(32.dp)) }
                    item {
                        Text(
                            text = "View all episodes",
                            color = RickAction,
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(horizontal = 32.dp)
                                .border(
                                    width = 1.dp,
                                    color = RickAction,
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .clip(RoundedCornerShape(12.dp))
                                .clickable {
                                    onEpisodeClicked(characterId)
                                }
                                .padding(vertical = 8.dp)
                                .fillMaxWidth()
                        )
                    }
                    item { Spacer(modifier = Modifier.height(64.dp)) }
                }
            }
        }
    }
}