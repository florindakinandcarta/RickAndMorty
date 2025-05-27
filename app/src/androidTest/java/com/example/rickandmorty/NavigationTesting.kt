package com.example.rickandmorty


import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.network.KtorClient
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import io.mockk.mockk
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class NavigationTesting {

    private lateinit var navController: TestNavHostController
    private lateinit var homeLabel: String
    private lateinit var episodesLabel: String
    private lateinit var searchLabel: String


    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()


    private val mockKtorClient = mockk<KtorClient>(relaxed = true)

    @Before
    fun setup() {
        hiltRule.inject()
        composeTestRule.setContent {
            homeLabel = "home_screen"
            episodesLabel = "episodes"
            searchLabel = "search"
            navController = TestNavHostController((ApplicationProvider.getApplicationContext()))
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            NavigationGraph(
                navController = navController,
                ktorClient = mockKtorClient,
                innerPadding = PaddingValues()
            )
        }
    }

    @Test
    fun verifyStartDestinationIsHome() {
        assertTrue(navController.currentDestination?.route == homeLabel)
    }
}