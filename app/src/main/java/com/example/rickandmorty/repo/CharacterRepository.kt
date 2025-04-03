package com.example.rickandmorty.repo

import com.example.network.ApiOperation
import com.example.network.KtorClient
import com.example.network.models.domain.Character
import com.example.network.models.domain.CharacterPage
import javax.inject.Inject

class CharacterRepository @Inject constructor(private val ktorClient: KtorClient) {
    suspend fun fetchCharacter(characterId: Int): ApiOperation<Character> {
        return ktorClient.getCharacter(characterId)
    }

    suspend fun fetCharacterPage(page: Int): ApiOperation<CharacterPage> {
        return ktorClient.getCharacterByPage(page)
    }
}