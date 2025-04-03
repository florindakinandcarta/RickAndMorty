package com.example.rickandmorty.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.rickandmorty.states.HomeScreenViewState
import com.example.rickandmorty.ui.components.CharacterGridItem
import com.example.rickandmorty.ui.components.LoadingState

@Composable
fun HomeScreen(
    onCharacterSelected: (Int) -> Unit,
    viewModel: HomeScreenViewModel = hiltViewModel()
) {
    val viewState by viewModel.viewState.collectAsState()
    LaunchedEffect(viewModel) {
        viewModel.fetchInitialPage()
    }
    when (val state = viewState) {
        is HomeScreenViewState.GridDisplay -> {
            LazyVerticalGrid(
                contentPadding = PaddingValues(vertical = 42.dp, horizontal = 16.dp),
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(items = state.characters, key = { it.id }) { character ->
                    CharacterGridItem(modifier = Modifier, character = character) {
                        onCharacterSelected(character.id)
                    }
                }
            }
        }

        HomeScreenViewState.Loading -> LoadingState()
    }
}