package com.shkurta.billy.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
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
                onNavigateToNewEntry = { entryId ->
                    if (entryId != null) {
                        navController.navigate("new_entry?entryId=$entryId")
                    } else {
                        navController.navigate("new_entry")
                    }
                }
            )
        }
        composable(
            route = "new_entry?entryId={entryId}",
            arguments = listOf(
                navArgument("entryId") {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) { backStackEntry ->
            val entryId = backStackEntry.arguments?.getInt("entryId") ?: -1
            NewEntryScreen(
                entryId = entryId,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
