package com.example.limaguide.UI1.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.limaguide.R
import com.example.limaguide.data.Place
import com.example.limaguide.model.LimaUiState
import com.example.limaguide.UI1.utils.ContentType

@Composable
fun RecommendationsScreen(
    uiState: LimaUiState,
    onPlaceClicked: (Int) -> Unit,
    onBackPressed: () -> Unit,
    contentType: ContentType,
    isDetailOnly: Boolean = false
) {
    // Maneja el botón de retroceso del sistema
    BackHandler {
        onBackPressed()
    }

    // Para pantallas grandes (tablet/foldable), se muestra lista y detalle
    if (contentType == ContentType.LIST_AND_DETAIL) {
        Row(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier.weight(1f)) {
                RecommendationsList(
                    places = uiState.recommendations,
                    onPlaceClicked = onPlaceClicked,
                    onBackPressed = onBackPressed,
                    categoryName = stringResource(id = uiState.currentCategory?.name ?: R.string.recommendations)
                )
            }
            Box(modifier = Modifier.weight(1.5f)) {
                // Muestra el detalle del lugar seleccionado
                uiState.currentPlace?.let {
                    PlaceDetail(place = it)
                }
            }
        }
    } else { // Para pantallas pequeñas, se muestra lista o detalle, pero no ambos
        if (isDetailOnly) {
            uiState.currentPlace?.let {
                PlaceDetail(
                    place = it,
                    onBackPressed = onBackPressed,
                    isFullScreen = true
                )
            }
        } else {
            RecommendationsList(
                places = uiState.recommendations,
                onPlaceClicked = onPlaceClicked,
                onBackPressed = onBackPressed,
                categoryName = stringResource(id = uiState.currentCategory?.name ?: R.string.recommendations)
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecommendationsList(
    places: List<Place>,
    onPlaceClicked: (Int) -> Unit,
    onBackPressed: () -> Unit,
    categoryName: String,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(categoryName) },
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back_button)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(places) { place ->
                PlaceListItem(
                    place = place,
                    onPlaceClicked = { onPlaceClicked(place.id) }
                )
            }
        }
    }
}

@Composable
fun PlaceListItem(
    place: Place,
    onPlaceClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onPlaceClicked),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = place.image),
                contentDescription = null,
                modifier = Modifier.size(100.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = stringResource(id = place.name),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(end = 16.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaceDetail(
    place: Place,
    onBackPressed: (() -> Unit)? = null,
    isFullScreen: Boolean = false,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            // Solo muestra la TopAppBar si es una pantalla completa de detalle
            if (isFullScreen && onBackPressed != null) {
                TopAppBar(
                    title = { Text(stringResource(id = place.name)) },
                    navigationIcon = {
                        IconButton(onClick = onBackPressed) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = stringResource(R.string.back_button)
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Image(
                    painter = painterResource(id = place.image),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp),
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(id = place.name),
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(id = place.description),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
