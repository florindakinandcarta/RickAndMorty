package com.example.rickandmorty.viewmodels

import com.example.network.ApiOperation
import com.example.network.KtorClient
import com.example.network.models.domain.Character
import com.example.network.models.domain.CharacterGender
import com.example.network.models.domain.CharacterPage
import com.example.network.models.domain.CharacterStatus
import com.example.rickandmorty.repo.CharacterRepository
import com.example.rickandmorty.states.HomeScreenViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

class HomeScreenViewModelTest {
    @Mock
    private lateinit var ktorClient: KtorClient

    private lateinit var characterRepository: CharacterRepository
    private lateinit var homeScreenViewModel: HomeScreenViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        characterRepository = CharacterRepository(ktorClient)
        homeScreenViewModel = HomeScreenViewModel(characterRepository)
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun fetchInitialPage_should_return_if_pageNotEmpty() {
        runTest {
            val fakeInfo = CharacterPage.Info(
                count = 1,
                pages = 1,
                next = null,
                prev = null
            )
            val fakeCharacterList = emptyList<Character>()
            val fakeCharacterPage = CharacterPage(
                info = fakeInfo,
                characters = fakeCharacterList
            )
            homeScreenViewModel.apply {
                val field =
                    HomeScreenViewModel::class.java.getDeclaredField("fetchedCharacterPages")
                field.isAccessible = true
                val list = field.get(this) as MutableList<CharacterPage>
                list.add(fakeCharacterPage)
            }
            homeScreenViewModel.fetchInitialPage()
            assertEquals(HomeScreenViewState.Loading, homeScreenViewModel.viewState.value)
        }
    }

    @Test
    fun fetchInitialPage_showsLoading_thenSuccess() {
        runTest {
            val fakeInfo = CharacterPage.Info(
                count = 1,
                pages = 1,
                next = null,
                prev = null
            )
            val fakeCharacterList = emptyList<Character>()
            val fakeCharacterPage = CharacterPage(
                info = fakeInfo,
                characters = fakeCharacterList
            )

            whenever(characterRepository.fetchCharacterPage(1)).thenReturn(
                ApiOperation.Success(
                    fakeCharacterPage
                )
            )

            homeScreenViewModel.fetchInitialPage()
            assertEquals(HomeScreenViewState.Loading, homeScreenViewModel.viewState.value)

            testDispatcher.scheduler.advanceUntilIdle()

            val expectedState = HomeScreenViewState.GridDisplay(fakeCharacterList)
            assertEquals(expectedState, homeScreenViewModel.viewState.value)
        }
    }

    @Test
    fun fetchNextEpisode_showsLoading_thenSuccess_withNewCharacters() {
        runTest {
            val fakeInfo = CharacterPage.Info(
                count = 1,
                pages = 1,
                next = null,
                prev = null
            )
            val fakeCharacters = listOf(
                Character(
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
                ),
                Character(
                    id = 2,
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
            )
            val fakeCharacterPage = CharacterPage(
                info = fakeInfo,
                characters = fakeCharacters
            )

            whenever(characterRepository.fetchCharacterPage(1)).thenReturn(
                ApiOperation.Success(
                    fakeCharacterPage
                )
            )
            homeScreenViewModel.fetchNextEpisode()
            assertEquals(HomeScreenViewState.Loading, homeScreenViewModel.viewState.value)

            testDispatcher.scheduler.advanceUntilIdle()

            val state = homeScreenViewModel.viewState.value as HomeScreenViewState.GridDisplay
            assertEquals(fakeCharacters, state.characters)
        }
    }
}