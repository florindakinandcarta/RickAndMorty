package com.example.rickandmorty.states

import com.example.network.models.domain.Character

sealed interface HomeScreenViewState {
    object Loading : HomeScreenViewState
    data class GridDisplay(
        val characters: List<Character> = emptyList()
    ) : HomeScreenViewState
}