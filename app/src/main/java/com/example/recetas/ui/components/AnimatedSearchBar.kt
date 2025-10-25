package com.example.recetas.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.recetas.ui.viewmodel.RecetaViewModel

@Composable
fun AnimatedSearchBar(
    modifier: Modifier = Modifier,
    viewModel: RecetaViewModel
){

    var isSearchActive by remember { mutableStateOf(false) }
    val query by viewModel.searchQuery.collectAsState()
    val animatedWidth by animateDpAsState(
        targetValue = if (isSearchActive) 250.dp else 48.dp,
        animationSpec = tween(durationMillis = 300)

    )

    val animatedAlpha by animateFloatAsState(
        targetValue = if (isSearchActive) 1f else 0f,
        animationSpec = tween(durationMillis = 300)
    )

    Row(
        modifier = modifier
            .height(56.dp)
            .padding(end = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        AnimatedVisibility(
            visible = isSearchActive,
            enter = fadeIn() + expandHorizontally(),
            exit = fadeOut() + shrinkHorizontally()
        ) {
            Box(
                modifier = Modifier
                    .width(animatedWidth)
                    .alpha(animatedAlpha)
            ) {
                TextField(
                    value = query,
                    onValueChange = {
                        viewModel.onSearchQueryChanged(it)
                        if (it.isNotEmpty()) viewModel.searchRecetas()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    placeholder = { Text("Buscar recetas...") },
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    trailingIcon = {
                        if (query.isNotEmpty()) {
                            IconButton(
                                onClick = {
                                    viewModel.onSearchQueryChanged("")
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Limpiar búsqueda"
                                )
                            }
                        }
                    }
                )
            }
        }

        IconButton(
            onClick = {
                isSearchActive = !isSearchActive
                if (!isSearchActive) {
                    viewModel.onSearchQueryChanged("")
                }
            }
        ) {
            Icon(
                imageVector = if (isSearchActive) Icons.Default.Close else Icons.Default.Search,
                contentDescription = if (isSearchActive) "Cerrar búsqueda" else "Buscar"
            )
        }
    }
}