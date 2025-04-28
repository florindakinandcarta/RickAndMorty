package com.example.rickandmorty.viewmodels

import com.example.network.ApiOperation
import com.example.network.KtorClient
import com.example.network.models.domain.Episode
import com.example.rickandmorty.repo.EpisodesRepository
import com.example.rickandmorty.states.AllEpisodesUiState
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

class AllEpisodesViewModelTest {

    @Mock
    private lateinit var ktorClient: KtorClient


    private lateinit var allEpisodesViewModel: AllEpisodesViewModel
    private lateinit var episodesRepository: EpisodesRepository


    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        episodesRepository = EpisodesRepository(ktorClient)
        allEpisodesViewModel = AllEpisodesViewModel(episodesRepository)
    }

    @Test
    fun refreshAllEpisodesWithTrueShouldEmitLoading() {
        runTest {
            whenever(episodesRepository.fetchAllEpisodes())
                .thenReturn(ApiOperation.Success(emptyList()))
            allEpisodesViewModel.refreshAllEpisodes(forceRefresh = true)
            val state = allEpisodesViewModel.uiState.first()
            assertEquals(AllEpisodesUiState.Loading, state)
        }
    }

    @Test
    fun testSuccessStateIsEmitted() {
        runTest {
            val mockEpisodesList = listOf(
                Episode(
                    id = 1,
                    name = "Episode 1",
                    seasonNumber = 2,
                    airDate = "28/04/2025",
                    characterIdsInEpisode = listOf(1, 2, 3, 4),
                    episodeNumber = 4
                ),
                Episode(
                    id = 2,
                    name = "Episode 2",
                    seasonNumber = 2,
                    airDate = "28/04/2025",
                    characterIdsInEpisode = listOf(1, 2, 3, 4),
                    episodeNumber = 4
                ),
                Episode(
                    id = 2,
                    name = "Episode 3",
                    seasonNumber = 2,
                    airDate = "28/04/2025",
                    characterIdsInEpisode = listOf(1, 2, 3, 4),
                    episodeNumber = 4
                )

            )
            whenever(episodesRepository.fetchAllEpisodes()).thenReturn(
                ApiOperation.Success(
                    mockEpisodesList
                )
            )
            allEpisodesViewModel.refreshAllEpisodes()
            val state = allEpisodesViewModel.uiState.first { it is AllEpisodesUiState.Success }
            assertTrue(state is AllEpisodesUiState.Success)
            assertTrue((state as AllEpisodesUiState.Success).data.isNotEmpty())
        }
    }

    @Test
    fun testErrorStateIsEmitted() {
        runTest {
            val exception = Exception("Failed to fetch episodes")
            whenever(episodesRepository.fetchAllEpisodes()).thenReturn(
                ApiOperation.Failure(
                    exception
                )
            )
            allEpisodesViewModel.refreshAllEpisodes()
            val state = allEpisodesViewModel.uiState.first { it is AllEpisodesUiState.Error }
            assertTrue(state is AllEpisodesUiState.Error)
        }
    }
}