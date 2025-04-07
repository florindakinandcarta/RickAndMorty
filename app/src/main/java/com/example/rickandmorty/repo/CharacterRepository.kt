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

    suspend fun fetCharacterPage(
        page: Int,
        params: Map<String, String> = emptyMap()
    ): ApiOperation<CharacterPage> {
        return ktorClient.getCharacterByPage(pageNumber = page, queryParams = params)
    }


    suspend fun fetchAllCharactersByName(searchQuery: String): ApiOperation<List<Character>> {
        return ktorClient.searchAllCharactersByName(searchQuery)
    }
}