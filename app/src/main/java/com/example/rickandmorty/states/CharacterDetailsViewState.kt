package com.example.rickandmorty.states

import com.example.network.models.domain.Character
import com.example.rickandmorty.ui.comoon.DataPoint

sealed interface CharacterDetailsViewState {
    object Loading : CharacterDetailsViewState
    data class Error(val message: String) : CharacterDetailsViewState
    data class Success(
        val character: Character,
        val characterDataPoint: List<DataPoint>
    ) : CharacterDetailsViewState
}