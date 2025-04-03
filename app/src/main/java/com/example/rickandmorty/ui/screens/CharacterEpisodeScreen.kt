package com.example.rickandmorty.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.network.KtorClient
import com.example.network.models.domain.Character
import com.example.network.models.domain.Episode
import com.example.rickandmorty.ui.comoon.CharacterImage
import com.example.rickandmorty.ui.comoon.CharacterNameComponent
import com.example.rickandmorty.ui.comoon.DataPoint
import com.example.rickandmorty.ui.comoon.DataPointComponent
import com.example.rickandmorty.ui.comoon.LoadingState
import com.example.rickandmorty.ui.comoon.SimpleToolbar
import com.example.rickandmorty.ui.components.EpisodeRowComponent
import com.example.rickandmorty.ui.components.SeasonHeader
import kotlinx.coroutines.launch

@Composable
fun CharacterEpisodeScreen(characterId: Int, ktorClient: KtorClient, onBackClicked: () -> Unit) {
    var characterState by remember {
        mutableStateOf<Character?>(null)
    }
    var episodesState by remember {
        mutableStateOf<List<Episode>>(emptyList())
    }

    LaunchedEffect(Unit) {
        ktorClient.getCharacter(characterId)
            .onSuccess { character ->
                characterState = character
                launch {
                    ktorClient.getEpisodes(character.episodeIds)
                        .onSuccess { episodes ->
                            episodesState = episodes
                        }
                        .onFailure {
                            //TODO
                        }
                }
            }
            .onFailure {
                //TODO
            }
    }
    characterState?.let { character ->
        MainScreen(character = character, episodes = episodesState, onBackClicked = onBackClicked)
    } ?: LoadingState()
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreen(character: Character, episodes: List<Episode>, onBackClicked: () -> Unit) {
    val episodeBySeasonMap = episodes.groupBy { it.seasonNumber }
    Column {
        SimpleToolbar(title = "Character episodes", onBackAction = onBackClicked)
        LazyColumn(contentPadding = PaddingValues(16.dp)) {
            item { CharacterNameComponent(name = character.name) }
            item { Spacer(modifier = Modifier.height(8.dp)) }
            item {
                LazyRow {
                    episodeBySeasonMap.forEach { mapEntry ->
                        val title = "Season ${mapEntry.key}"
                        val description = "${mapEntry.value.size} ep"
                        item {
                            DataPointComponent(dataPoint = DataPoint(title, description))
                            Spacer(modifier = Modifier.width(32.dp))
                        }
                    }
                }
            }
            item { Spacer(modifier = Modifier.height(16.dp)) }
            item { CharacterImage(imageUrl = character.imageUrl) }
            item { Spacer(modifier = Modifier.height(32.dp)) }

            episodeBySeasonMap.forEach { mapEntry ->
                stickyHeader { SeasonHeader(seasonNumber = mapEntry.key) }
                item { Spacer(modifier = Modifier.height(16.dp)) }
                items(mapEntry.value) { episode ->
                    EpisodeRowComponent(episode = episode)
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}