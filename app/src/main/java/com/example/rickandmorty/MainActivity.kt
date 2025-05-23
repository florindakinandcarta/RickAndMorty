package com.example.rickandmorty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.network.KtorClient
import com.example.rickandmorty.screens.AllEpisodesScreen
import com.example.rickandmorty.screens.CharacterDetailsScreen
import com.example.rickandmorty.screens.CharacterEpisodeScreen
import com.example.rickandmorty.screens.HomeScreen
import com.example.rickandmorty.screens.SearchScreen
import com.example.rickandmorty.ui.theme.RickAction
import com.example.rickandmorty.ui.theme.RickAndMortyTheme
import com.example.rickandmorty.ui.theme.RickPrimary
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var ktorClient: KtorClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val items = listOf(
                NavDestination.Home, NavDestination.Episodes, NavDestination.Search
            )
            var selectedIndex by remember {
                mutableIntStateOf(0)
            }

            RickAndMortyTheme {
                Scaffold(
                    bottomBar = {
                        NavigationBar(
                            containerColor = RickPrimary
                        ) {
                            items.forEachIndexed { index, topLevelRoute ->
                                NavigationBarItem(
                                    icon = {
                                        Icon(
                                            topLevelRoute.icon,
                                            contentDescription = topLevelRoute.title
                                        )
                                    },
                                    label = { Text(topLevelRoute.title) },
                                    selected = index == selectedIndex,
                                    onClick = {
                                        selectedIndex = index
                                        navController.navigate(topLevelRoute.route) {
                                            popUpTo(navController.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    },
                                    colors = NavigationBarItemDefaults.colors(
                                        selectedIconColor = RickAction,
                                        selectedTextColor = RickAction,
                                        indicatorColor = Color.Transparent
                                    )
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "home_screen",
                        modifier = Modifier
                            .background(color = RickPrimary)
                            .padding(innerPadding)
                    ) {
                        composable(route = "home_screen") {
                            HomeScreen(onCharacterSelected = { characterId ->
                                navController.navigate("character_details/$characterId")
                            })
                        }
                        composable(
                            "character_details/{characterId}", arguments = listOf(
                                navArgument("characterId") { type = NavType.IntType })
                        ) { navBackStackEntry ->
                            val characterId: Int =
                                navBackStackEntry.arguments?.getInt("characterId") ?: -1
                            CharacterDetailsScreen(characterId = characterId,
                                onBackClicked = {
                                    navController.navigateUp()
                                },
                                onEpisodeClicked = {
                                    navController.navigate("character_episodes/$it")
                                })
                        }
                        composable("character_episodes/{characterId}", arguments = listOf(
                            navArgument("characterId") { type = NavType.IntType }
                        )) { navBackStackEntry ->
                            val characterId: Int =
                                navBackStackEntry.arguments?.getInt("characterId") ?: -1
                            CharacterEpisodeScreen(
                                characterId = characterId,
                                ktorClient = ktorClient,
                                onBackClicked = {
                                    navController.navigateUp()
                                }
                            )
                        }

                        composable(route = NavDestination.Episodes.route) {
                            Column(
                                modifier = Modifier.fillMaxSize()
                            ) {
                                AllEpisodesScreen()
                            }
                        }
                        composable(route = NavDestination.Search.route) {
                            SearchScreen(
                                onCharacterClicked = {characterId ->
                                    navController.navigate("character_details/$characterId")
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
