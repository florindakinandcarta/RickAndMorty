package com.example.rickandmorty.viewmodels

import app.cash.turbine.test
import com.example.network.ApiOperation
import com.example.network.KtorClient
import com.example.network.models.domain.Character
import com.example.network.models.domain.CharacterGender
import com.example.network.models.domain.CharacterStatus
import com.example.rickandmorty.repo.CharacterRepository
import com.example.rickandmorty.states.CharacterDetailsViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever


@RunWith(MockitoJUnitRunner::class)
class CharacterDetailsViewModelTest {
    @Mock
    val ktorClient = mock<KtorClient>()

    private val characterRepository = CharacterRepository(ktorClient)

    private val viewModel = CharacterDetailsViewModel(characterRepository)

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `fetch character with success response emits Loading and then Success`() =
        runTest {
            val mockCharacter = Character(
                id = 1,
                name = "Rick Sanchez",
                species = "Human",
                gender = CharacterGender.Male,
                type = "Scientist",
                origin = Character.Origin(name = "Earth", url = "wert"),
                location = Character.Location(name = "Citadel of Ricks", url = "wert"),
                episodeIds = listOf(1, 2, 3),
                imageUrl = "wertrew",
                created = "23er",
                status = CharacterStatus.Alive
            )
            whenever(characterRepository.fetchCharacter(1))
                .thenReturn(ApiOperation.Success(mockCharacter))

            viewModel.stateFlow.test {
                viewModel.fetchCharacter(1)

                assertEquals(CharacterDetailsViewState.Loading, awaitItem())

                val successState = awaitItem()
                assertTrue(successState is CharacterDetailsViewState.Success)
                val success = successState as CharacterDetailsViewState.Success
                assertEquals(mockCharacter, success.character)
                assertTrue(success.characterDataPoint.any { it.title == "Species" && it.description == "Human" })
                assertTrue(success.characterDataPoint.any { it.title == "Episode count" && it.description == "3" })

                cancelAndIgnoreRemainingEvents()
            }
        }


    @Test
    fun `fetch character with error response emits Loading then error`() = runTest {
        val errorMessage = "Character not found"
        whenever(characterRepository.fetchCharacter(999))
            .thenReturn(ApiOperation.Failure(Exception(errorMessage)))

        viewModel.stateFlow.test {
            viewModel.fetchCharacter(999)
            assertEquals(CharacterDetailsViewState.Loading, awaitItem())
            val errorState = awaitItem()
            assertTrue(errorState is CharacterDetailsViewState.Error)
            val error = errorState as CharacterDetailsViewState.Error
            assertEquals(errorMessage, error.message)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

}