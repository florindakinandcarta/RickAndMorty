package com.example.rickandmorty.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.repo.CharacterRepository
import com.example.rickandmorty.states.CharacterDetailsViewState
import com.example.rickandmorty.ui.components.DataPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailsViewModel @Inject constructor(
    private val characterRepository: CharacterRepository
) : ViewModel() {
    private val _internalStorageFlow =
        MutableStateFlow<CharacterDetailsViewState>(CharacterDetailsViewState.Loading)
    val stateFlow = _internalStorageFlow.asStateFlow()

    fun fetchCharacter(characterId: Int) = viewModelScope.launch {
        _internalStorageFlow.update { return@update CharacterDetailsViewState.Loading }
        characterRepository.fetchCharacter(characterId).onSuccess { character ->
            val dataPoints = buildList {
                add(DataPoint("Last known location", character.location.name))
                add(DataPoint("Species", character.species))
                add(DataPoint("Gender", character.gender.displayName))
                character.type.takeIf {
                    it.isNotEmpty()
                }?.let { type ->
                    add(DataPoint("Type", type))
                }
                add(DataPoint("Origin", character.origin.name))
                add(DataPoint("Episode count", character.episodeIds.size.toString()))
            }
            _internalStorageFlow.update {
                return@update CharacterDetailsViewState.Success(
                    character = character,
                    characterDataPoint = dataPoints
                )
            }

        }.onFailure { exception ->
            _internalStorageFlow.update {
                return@update CharacterDetailsViewState.Error(
                    exception.message ?: "Unknown error occurred"
                )
            }
        }
    }
}

