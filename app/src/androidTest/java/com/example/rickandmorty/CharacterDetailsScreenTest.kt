package com.example.rickandmorty

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.network.models.domain.Character
import com.example.network.models.domain.CharacterGender
import com.example.network.models.domain.CharacterStatus
import com.example.rickandmorty.states.CharacterDetailsViewState
import com.example.rickandmorty.ui.comoon.DataPoint
import com.example.rickandmorty.ui.comoon.DataPointComponent
import com.example.rickandmorty.ui.comoon.SimpleToolbar
import com.example.rickandmorty.ui.components.CharacterDetailsNamePlateComponent
import com.example.rickandmorty.ui.theme.RickAction
import org.junit.Rule
import org.junit.Test

class CharacterDetailsScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()
    var backClicked = false
    val clickedEpisodes = mutableListOf<Int>()
    val fakeCharacter = Character(
        created = "2023-01-01T00:00:00.000Z",
        episodeIds = listOf(1, 2, 3),
        gender = CharacterGender.Male,
        id = 42,
        imageUrl = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
        location = Character.Location(
            name = "Citadel of Ricks",
            url = "https://rickandmortyapi.com/api/location/3"
        ),
        name = "Rick Sanchez",
        origin = Character.Origin(
            name = "Earth (C-137)",
            url = "https://rickandmortyapi.com/api/location/1"
        ),
        species = "Human",
        status = CharacterStatus.Alive,
        type = ""
    )

    val fakeState = CharacterDetailsViewState.Success(
        character = fakeCharacter,
        characterDataPoint = listOf(
            DataPoint("Species", "Human"),
            DataPoint("Gender", "Male"),
            DataPoint("Origin", "Earth (C-137)")
        )
    )

    @Test
    fun simpleToolbarTest() {
        composeTestRule.setContent {
            SimpleToolbar(
                title = "Hello there!",
                onBackAction = {
                    backClicked = true
                }
            )
        }
        composeTestRule.onNodeWithContentDescription("Back Arrow").performClick()
        assert(backClicked)
    }

    @Test
    fun lazyColumnTest() {
        composeTestRule.setContent {
            Column {
                SimpleToolbar(title = "Char details", onBackAction = {})
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    item {
                        CharacterDetailsNamePlateComponent(
                            name = fakeState.character.name,
                            status = fakeState.character.status
                        )
                    }
                    items(fakeState.characterDataPoint) {
                        DataPointComponent(dataPoint = it)
                    }
                    item {
                        Text(
                            text = "View all episodes",
                            color = RickAction,
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .clickable { clickedEpisodes.add(fakeState.character.id) }
                                .padding(vertical = 8.dp)
                        )
                    }
                }
            }
        }
        composeTestRule.onNodeWithText("Rick Sanchez").assertIsDisplayed()
        composeTestRule.onNodeWithText("Species").assertIsDisplayed()
        composeTestRule.onNodeWithText("View all episodes").assertIsDisplayed().performClick()
        assert(clickedEpisodes.contains(42))
    }

}