package com.example.limaguide.UI1

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.limaguide.UI1.screens.CategoriesScreen
import com.example.limaguide.UI1.screens.RecommendationsScreen
import com.example.limaguide.data.LocalDataProvider
import com.example.limaguide.UI1.LimaViewModel
import com.example.limaguide.UI1.utils.ContentType

// Enum para definir las rutas de navegación de forma segura
enum class LimaScreen {
    Categories,
    Recommendations,
    Details
}

@Composable
fun LimaApp(
    windowSize: WindowWidthSizeClass,
    navController: NavHostController = rememberNavController()
) {
    val viewModel: LimaViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()

    // Determina el tipo de contenido a mostrar basado en el tamaño de la pantalla
    val contentType = when (windowSize) {
        WindowWidthSizeClass.Compact,
        WindowWidthSizeClass.Medium -> ContentType.LIST_ONLY
        WindowWidthSizeClass.Expanded -> ContentType.LIST_AND_DETAIL
        else -> ContentType.LIST_ONLY
    }

    NavHost(
        navController = navController,
        startDestination = LimaScreen.Categories.name,
    ) {
        // Pantalla de Categorías
        composable(route = LimaScreen.Categories.name) {
            CategoriesScreen(
                categories = LocalDataProvider.allCategories,
                onCategoryClicked = { categoryId ->
                    viewModel.updateCurrentCategory(categoryId)
                    navController.navigate(LimaScreen.Recommendations.name)
                },
                contentType = contentType
            )
        }

        // Pantalla de Recomendaciones
        composable(route = LimaScreen.Recommendations.name) {
            RecommendationsScreen(
                uiState = uiState,
                onPlaceClicked = { placeId ->
                    viewModel.updateCurrentPlace(placeId)
                    // En pantallas grandes, no navegamos, solo actualizamos el detalle
                    if (contentType == ContentType.LIST_ONLY) {
                        navController.navigate(LimaScreen.Details.name)
                    }
                },
                onBackPressed = {
                    navController.popBackStack()
                },
                contentType = contentType
            )
        }

        // Pantalla de Detalle (solo para pantallas compactas/medianas)
        composable(route = LimaScreen.Details.name) {
            // La pantalla de detalle se muestra dentro de RecommendationsScreen para pantallas grandes,
            // así que aquí solo manejamos la navegación hacia atrás.
            // El contenido real del detalle se obtiene del uiState.
            // Esta ruta es un marcador para la pila de navegación en pantallas pequeñas.
            RecommendationsScreen(
                uiState = uiState,
                onPlaceClicked = {},
                onBackPressed = {
                    navController.popBackStack()
                },
                contentType = contentType,
                isDetailOnly = true // Indicamos que solo muestre el detalle
            )
        }
    }
}
