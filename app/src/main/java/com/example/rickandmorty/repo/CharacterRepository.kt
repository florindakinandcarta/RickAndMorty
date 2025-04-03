package com.example.rickandmorty.repo

import com.example.network.ApiOperation
import com.example.network.KtorClient
import com.example.network.models.domain.Character
import javax.inject.Inject

class CharacterRepository @Inject constructor(private val ktorClient: KtorClient) {
    suspend fun fetchCharacter(characterId: Int): ApiOperation<Character> {
        return ktorClient.getCharacter(characterId)
    }
}