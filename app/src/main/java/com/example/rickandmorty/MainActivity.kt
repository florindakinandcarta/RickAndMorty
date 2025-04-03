package com.example.rickandmorty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.network.KtorClient
import com.example.rickandmorty.ui.screens.CharacterDetailsScreen
import com.example.rickandmorty.ui.screens.CharacterEpisodeScreen
import com.example.rickandmorty.ui.screens.HomeScreen
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
            RickAndMortyTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = RickPrimary
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = "home_screen",
                        modifier = Modifier.padding(top = 32.dp)
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
                    }
                }
            }
        }
    }
}
