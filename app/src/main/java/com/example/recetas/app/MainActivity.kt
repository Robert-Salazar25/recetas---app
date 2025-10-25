package com.example.recetas.app

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.compose.RecetasAppTheme
import com.example.recetas.ui.navigation.NavGraph
import com.example.recetas.ui.viewmodel.RecetaViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var viewModel: RecetaViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            RecetasAppTheme {
                val navController = rememberNavController()
                var backPressedState by remember { mutableStateOf(true) }
                var backPressedTime by remember { mutableStateOf(0L) }

                // Interceptar el botón atrás correctamente
                BackHandler {
                    val currentRoute = navController.currentBackStackEntry?.destination?.route

                    when {
                        // Si estamos en la pantalla de búsqueda, cerrarla
                        currentRoute == "recetaList" && viewModel.isSearchActive.value -> {
                            viewModel.toggleSearch(false)
                        }
                        // Si no estamos en la pantalla principal, volver atrás
                        navController.previousBackStackEntry != null -> {
                            navController.popBackStack()
                        }
                        // Si estamos en la pantalla principal, confirmar salida
                        else -> {
                            if (backPressedState && (System.currentTimeMillis() - backPressedTime) > 2000) {
                                backPressedState = false
                                backPressedTime = System.currentTimeMillis()
                                Toast.makeText(
                                    this,
                                    "Presiona de nuevo para salir",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                finish()
                            }
                        }
                    }
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavGraph(navController = navController)
                }
            }
        }
    }
}