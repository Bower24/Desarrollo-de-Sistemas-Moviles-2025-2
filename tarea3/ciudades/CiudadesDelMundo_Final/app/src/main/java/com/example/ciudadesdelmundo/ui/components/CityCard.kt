package com.example.ciudadesdelmundo.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.ciudadesdelmundo.model.City
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color

@Composable
fun CityCard(city: City, modifier: Modifier = Modifier) {
    var expanded by remember { mutableStateOf(false) }
    var showOverlay by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .padding(8.dp)
            .animateContentSize(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Imagen de la ciudad
            Box(
                modifier = Modifier
                    .height(180.dp)
                    .fillMaxWidth()
            ) {
                AsyncImage(
                    model = city.imageUrl,
                    contentDescription = city.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                // Overlay con animación
                this@Column.AnimatedVisibility(
                    visible = showOverlay,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color(0x88000000))
                            .padding(12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = city.country, color = Color.White)
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Contenido textual y botones
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = "${city.name}, ${city.country}",
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.height(4.dp))

                // Botón para expandir/ocultar info
                Button(onClick = { expanded = !expanded }) {
                    Text(text = if (expanded) "Ocultar info" else "Ver info")
                }

                // Descripción visible si está expandido
                AnimatedVisibility(visible = expanded) {
                    Text(
                        text = city.description,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Botón para overlay
                OutlinedButton(onClick = { showOverlay = !showOverlay }) {
                    Text(text = if (showOverlay) "Ocultar overlay" else "Mostrar overlay")
                }
            }
        }
    }
}

