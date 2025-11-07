package com.example.flightsearch.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.flightsearch.R
import com.example.flightsearch.data.Airport
import com.example.flightsearch.data.Favorite

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlightScreen(viewModel: FlightViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(stringResource(R.string.app_name)) })
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues).padding(16.dp)) {
            SearchBar(
                query = uiState.searchQuery,
                onQueryChange = viewModel::onQueryChange
            )
            Spacer(Modifier.height(16.dp))

            when {
                uiState.searchQuery.isEmpty() -> {
                    // Muestra favoritos
                    val favorites by viewModel.getFavoritesStream().collectAsState(initial = emptyList())
                    FavoriteRoutesList(favorites)
                }
                uiState.selectedAirport == null -> {
                    // Muestra sugerencias
                    SuggestionsList(
                        suggestions = uiState.suggestions,
                        onSuggestionClick = viewModel::onAirportSelected
                    )
                }
                else -> {
                    // Muestra destinos
                    val destinations by viewModel
                        .getDestinationsStream(uiState.selectedAirport!!.iataCode)
                        .collectAsState(initial = emptyList())

                    DestinationList(
                        departureAirport = uiState.selectedAirport!!,
                        destinations = destinations,
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}

@Composable
fun SearchBar(query: String, onQueryChange: (String) -> Unit) {
    TextField(
        value = query,
        onValueChange = onQueryChange,
        placeholder = { Text("Enter airport name or code") },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true
    )
}

@Composable
fun SuggestionsList(suggestions: List<Airport>, onSuggestionClick: (Airport) -> Unit) {
    LazyColumn {
        items(suggestions) { airport ->
            SuggestionItem(airport = airport, onClick = { onSuggestionClick(airport) })
        }
    }
}

@Composable
fun SuggestionItem(airport: Airport, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp)
    ) {
        Text(text = airport.iataCode, fontWeight = FontWeight.Bold)
        Spacer(Modifier.width(8.dp))
        Text(text = airport.name)
    }
}

@Composable
fun DestinationList(
    departureAirport: Airport,
    destinations: List<Airport>,
    viewModel: FlightViewModel
) {
    Column {
        Text("Flights from ${departureAirport.name}", style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(8.dp))
        LazyColumn {
            items(destinations) { destination ->
                val isFavorite by viewModel.getFavoriteStream(
                    departureAirport.iataCode,
                    destination.iataCode
                ).collectAsState(initial = null)

                DestinationItem(
                    departure = departureAirport,
                    destination = destination,
                    isFavorite = isFavorite != null,
                    onFavoriteClick = {
                        viewModel.toggleFavorite(departureAirport.iataCode, destination.iataCode)
                    }
                )
            }
        }
    }
}

@Composable
fun DestinationItem(
    departure: Airport,
    destination: Airport,
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                Text("DEPART", style = MaterialTheme.typography.labelSmall)
                Text(
                    text = "${departure.iataCode} ${departure.name}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(Modifier.height(8.dp))
                Text("ARRIVE", style = MaterialTheme.typography.labelSmall)
                Text(
                    text = "${destination.iataCode} ${destination.name}",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            IconButton(onClick = onFavoriteClick) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Favorite",
                    tint = if (isFavorite) MaterialTheme.colorScheme.primary else LocalContentColor.current.copy(alpha = 0.5f)
                )
            }
        }
    }
}

@Composable
fun FavoriteRoutesList(favorites: List<Favorite>) {
    Column {
        if (favorites.isEmpty()) {
            Text("No favorite routes saved.")
        } else {
            Text("Favorite Routes", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(8.dp))
            LazyColumn {
                items(favorites) { favorite ->
                    FavoriteItem(favorite)
                }
            }
        }
    }
}

@Composable
fun FavoriteItem(favorite: Favorite) {
    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Row(modifier = Modifier.padding(16.dp)) {
            Column(modifier = Modifier.weight(1f)) {
                Text("DEPART", style = MaterialTheme.typography.labelSmall)
                Text(favorite.departureCode, style = MaterialTheme.typography.bodyLarge)
                Spacer(Modifier.height(8.dp))
                Text("ARRIVE", style = MaterialTheme.typography.labelSmall)
                Text(favorite.destinationCode, style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}