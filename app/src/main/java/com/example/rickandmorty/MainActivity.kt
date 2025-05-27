package com.example.rickandmorty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.rememberNavController
import com.example.network.KtorClient
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
                    NavigationGraph(
                        navController = navController,
                        ktorClient = ktorClient,
                        innerPadding = innerPadding
                    )
                }
            }
        }
    }
}
