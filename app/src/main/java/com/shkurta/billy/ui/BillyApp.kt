package com.shkurta.billy.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shkurta.billy.ui.screens.HomeScreen
import com.shkurta.billy.ui.screens.NewEntryScreen

@Composable
fun BillyApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(
                onNavigateToNewEntry = {
                    navController.navigate("new_entry")
                }
            )
        }
        composable("new_entry") {
            NewEntryScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
