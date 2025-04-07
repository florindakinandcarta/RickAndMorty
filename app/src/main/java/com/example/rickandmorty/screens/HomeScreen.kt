package com.example.rickandmorty.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.rickandmorty.states.HomeScreenViewState
import com.example.rickandmorty.ui.comoon.LoadingState
import com.example.rickandmorty.ui.comoon.SimpleToolbar
import com.example.rickandmorty.ui.components.CharacterGridItem
import com.example.rickandmorty.viewmodels.HomeScreenViewModel

@Composable
fun HomeScreen(
    onCharacterSelected: (Int) -> Unit,
    viewModel: HomeScreenViewModel = hiltViewModel()
) {
    val viewState by viewModel.viewState.collectAsState()
    LaunchedEffect(viewModel) {
        viewModel.fetchInitialPage()
    }
    val scrollState = rememberLazyGridState()
    val fetchNextPage: Boolean by remember {
        derivedStateOf {
            val currentCharacterCount =
                (viewState as? HomeScreenViewState.GridDisplay)?.characters?.size
                    ?: return@derivedStateOf false
            val lastDisplayIndex = scrollState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
                ?: return@derivedStateOf false
            return@derivedStateOf lastDisplayIndex >= currentCharacterCount - 10
        }
    }
    LaunchedEffect(fetchNextPage) {
        if (fetchNextPage) viewModel.fetchNextEpisode()
    }
    when (val state = viewState) {
        is HomeScreenViewState.GridDisplay -> {
            Column {
                SimpleToolbar(title = "All characters")
                LazyVerticalGrid(
                    contentPadding = PaddingValues(vertical = 16.dp, horizontal = 16.dp),
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
        }

        HomeScreenViewState.Loading -> LoadingState()
    }
}