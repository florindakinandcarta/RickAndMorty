package com.example.rickandmorty.viewmodels

import com.example.network.models.domain.CharacterStatus
import com.example.rickandmorty.repo.CharacterRepository
import io.mockk.mockk
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class SearchViewModelTest {
    private lateinit var searchViewModel: SearchViewModel
    private lateinit var mockRepository: CharacterRepository
    private val testDispatcher = StandardTestDispatcher()


    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        mockRepository = mockk(relaxed = true)
        searchViewModel = SearchViewModel(mockRepository)

        setViewModelState(
            SearchViewModel.ScreenState.Content(
                "",
                results = emptyList(),
                filterState = SearchViewModel.ScreenState.Content.FilterState(
                    statuses = listOf(
                        CharacterStatus.Alive,
                        CharacterStatus.Dead,
                        CharacterStatus.Unknown
                    ),
                    selectedStatuses = emptyList()
                )
            )

        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }


    @Test
    fun `toggleStatus adds status when not selected`() = runTest {
        searchViewModel.toggleStatus(CharacterStatus.Alive)
        val state = searchViewModel.uiState.value as SearchViewModel.ScreenState.Content
        assertTrue(CharacterStatus.Alive in state.filterState.selectedStatuses)
    }

    @Test
    fun `toggleStatus removes status when already selected`() = runTest {
        searchViewModel.toggleStatus(CharacterStatus.Alive)
        searchViewModel.toggleStatus(CharacterStatus.Alive)
        val state = searchViewModel.uiState.value as SearchViewModel.ScreenState.Content
        assertFalse(CharacterStatus.Alive in state.filterState.selectedStatuses)
    }


    private fun setViewModelState(state: SearchViewModel.ScreenState) {
        val uiStateField = SearchViewModel::class.java.getDeclaredField("_uiState")
        uiStateField.isAccessible = true
        val stateFlow =
            uiStateField.get(searchViewModel) as MutableStateFlow<SearchViewModel.ScreenState>
        stateFlow.value = state
    }

}