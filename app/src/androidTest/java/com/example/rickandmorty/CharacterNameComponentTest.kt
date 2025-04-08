package com.example.rickandmorty

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.rickandmorty.ui.comoon.CharacterNameComponent
import org.junit.Rule
import org.junit.Test

class CharacterNameComponentTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun characterNameComponent() {
        composeTestRule.setContent {
            CharacterNameComponent(
                name = "Florinda"
            )
        }
        composeTestRule.onNodeWithText("Florinda").assertIsDisplayed()
    }
}