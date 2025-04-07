package com.example.rickandmorty.states

import com.example.network.models.domain.Episode

sealed interface AllEpisodesUiState {
    object Error : AllEpisodesUiState
    object Loading : AllEpisodesUiState
    data class Success(val data: Map<String, List<Episode>>) : AllEpisodesUiState

}