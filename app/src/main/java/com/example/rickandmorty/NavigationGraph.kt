package com.example.rickandmorty

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import com.example.network.KtorClient
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.rickandmorty.screens.AllEpisodesScreen
import com.example.rickandmorty.screens.CharacterDetailsScreen
import com.example.rickandmorty.screens.CharacterEpisodeScreen
import com.example.rickandmorty.screens.HomeScreen
import com.example.rickandmorty.screens.SearchScreen
import com.example.rickandmorty.ui.theme.RickPrimary

@Composable
fun NavigationGraph(
    navController: NavHostController,
    ktorClient: KtorClient,
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = "home_screen",
        modifier = Modifier
            .background(color = RickPrimary)
            .padding(innerPadding)
    ){
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