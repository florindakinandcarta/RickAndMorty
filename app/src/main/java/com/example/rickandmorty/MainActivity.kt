package com.example.rickandmorty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.network.KtorClient
import com.example.rickandmorty.ui.screens.CharacterDetailsScreen
import com.example.rickandmorty.ui.theme.RickAndMortyTheme
import com.example.rickandmorty.ui.theme.RickPrimary

class MainActivity : ComponentActivity() {
    private val ktorClient = KtorClient()
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
                    NavHost(navController = navController, startDestination = "character_details") {
                        composable("character_details") {
                            CharacterDetailsScreen(
                                ktorClient = ktorClient,
                                characterId = 1
                            ) {
                                navController.navigate("character_episodes/$it")
                            }
                        }
                        composable("character_episodes/{characterId}", arguments = listOf(
                            navArgument("characterId") { type = NavType.IntType }
                        )) { navBackStackEntry ->
                            println("id: ${navBackStackEntry.arguments?.getInt("characterId") ?: -1}")
                        }
                    }
                }
            }
        }
    }
}
