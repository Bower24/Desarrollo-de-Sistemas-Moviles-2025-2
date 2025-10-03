package com.example.ciudadesdelmundo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.ciudadesdelmundo.ui.theme.CiudadesDelMundoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CiudadesDelMundoTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    CitiesScreen()
                }
            }
        }
    }
}

data class City(
    val name: String,
    val description: String,
    val imageRes: Int
)

@Composable
fun CitiesScreen() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        items(cities) { city ->
            CityCard(city)
        }
    }
}
val cities = listOf(
    City("París", "La ciudad del amor y la Torre Eiffel.", R.drawable.paris),
    City("Tokio", "Moderna y tradicional, llena de cultura.", R.drawable.tokyo),
    City("Nueva York", "La ciudad que nunca duerme.", R.drawable.nueva_york),
    City("Londres", "Historia, cultura y el Big Ben.", R.drawable.londres),
    City("Roma", "Cuna del Imperio Romano.", R.drawable.roma),
    City("Barcelona", "Gaudí y playas mediterráneas.", R.drawable.barcelona),
    City("Berlín", "Historia y modernidad en Alemania.", R.drawable.berlin),
    City("Sídney", "Famosa por su Ópera y playas.", R.drawable.sidney),
    City("Los Ángeles", "Hollywood y entretenimiento.", R.drawable.los_angeles),
    City("Dubái", "Lujo y arquitectura futurista.", R.drawable.dubai),
    City("Hong Kong", "Rascacielos y cultura oriental.", R.drawable.hong_kong),
    City("Bangkok", "Templos y vibrante vida nocturna.", R.drawable.bangkok),
    City("Moscú", "La Plaza Roja y la historia rusa.", R.drawable.moscu),
    City("Estambul", "Puente entre Europa y Asia.", R.drawable.estambul),
    City("Ámsterdam", "Canales y bicicletas.", R.drawable.amsterdam),
    City("Toronto", "Diversidad y la Torre CN.", R.drawable.toronto),
    City("Ciudad de México", "Historia azteca y cultura moderna.", R.drawable.ciudad_de_mexico),
    City("Buenos Aires", "Tango y tradición argentina.", R.drawable.buenos_aires),
    City("Lima", "Capital gastronómica de América.", R.drawable.lima),
    City("El Cairo", "Pirámides y cultura egipcia.", R.drawable.el_cairo),
    City("Atenas", "Cuna de la civilización griega.", R.drawable.atenas),
    City("Río de Janeiro", "Cristo Redentor y playas.", R.drawable.rio_de_janeiro),
    City("Venecia", "Canales románticos en Italia.", R.drawable.venecia),
    City("San Francisco", "Golden Gate y cultura hippie.", R.drawable.san_francisco),
    City("Seúl", "Tecnología y tradición coreana.", R.drawable.seul),
    City("Singapur", "Ciudad futurista del sudeste asiático.", R.drawable.singapur),
    City("Praga", "Arquitectura medieval impresionante.", R.drawable.praga),
    City("Varsovia", "Renacida tras la guerra.", R.drawable.varsovia),
    City("Oslo", "Naturaleza y diseño nórdico.", R.drawable.oslo),
    City("Estocolmo", "Belleza escandinava sobre el mar.", R.drawable.estocolmo)
)

@Composable
fun CityCard(city: City) {
    var expanded by remember { mutableStateOf(false) }
    var showOverlay by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = city.imageRes),
                    contentDescription = city.name,
                    modifier = Modifier
                        .size(80.dp)
                        .padding(end = 12.dp)
                )
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = city.name,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Botones de interacción
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.align(Alignment.End)
            ) {
                Button(onClick = { expanded = !expanded }) {
                    Text(if (expanded) "Ocultar" else "Ver más")
                }
                Button(onClick = { showOverlay = !showOverlay }) {
                    Text(if (showOverlay) "Ocultar dato extra" else "Dato extra")
                }
            }

            // Descripción expandible
            AnimatedVisibility(visible = expanded, enter = fadeIn(), exit = fadeOut()) {
                Text(
                    text = city.description,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            // Texto extra al presionar el segundo botón
            AnimatedVisibility(visible = showOverlay, enter = fadeIn(), exit = fadeOut()) {
                Text(
                    text = "📍 País relacionado: ${city.name}",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Light,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}

