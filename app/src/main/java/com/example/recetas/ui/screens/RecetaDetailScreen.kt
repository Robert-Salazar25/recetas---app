package com.example.recetas.ui.screens

// presentation/screens/RecetaDetailScreen.kt
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.recetas.ui.viewmodel.RecetaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecetaDetailScreen(
    id: String?,
    viewModel: RecetaViewModel = hiltViewModel()
) {
    // OBSERVAMOS el StateFlow correctamente
    val recetas by viewModel.recetas.collectAsState()


    // Buscamos la receta en la lista observada
    val receta = recetas.find { it.id == id }
    // Si no encontramos la receta, mostramos mensaje
    if (receta == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Receta no encontrada")
        }
        return
    }

    // Contenido principal con scroll
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // Imagen de la receta
        AsyncImage(
            model = receta.imagen,
            contentDescription = "Imagen de ${receta.nombre}",
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Nombre de la receta
        Text(
            text = receta.nombre,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(8.dp))

                Text(
                text = "Category: ${receta.categoria}",
            style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.secondary
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Sección de ingredientes
        Text(
            text = "Ingredients:",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(8.dp))
        Column {
            receta.ingredientes.forEach { ingrediente ->
                Text(
                    text = "• $ingrediente",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Instrucciones
        Text(
            text = "Instrucciones:",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = receta.instrucciones,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}