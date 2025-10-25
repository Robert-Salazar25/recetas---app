package com.example.recetas.ui.navigation

// presentation/navigation/NavGraph.kt
import RecetaListScreen
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavType
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.example.recetas.ui.screens.RecetaDetailScreen

@Composable
fun NavGraph(navController: NavHostController) {

    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStack?.destination

    NavHost(
        navController = navController,
        startDestination = "recetaList"
    ) {
        composable("recetaList") {
            RecetaListScreen(navController = navController)
        }

        composable(
            route = "recetaDetail/{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            RecetaDetailScreen(
                id = backStackEntry.arguments?.getString("id"),
            )
        }
    }

    BackHandler(
        enabled = currentDestination?.route == "recetaDetail/{id}",
        onBack = {navController.popBackStack()}
    )
}