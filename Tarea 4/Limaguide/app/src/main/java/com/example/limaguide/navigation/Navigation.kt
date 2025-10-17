package com.example.limaguide.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import com.example.limaguide.data.Category
import androidx.compose.ui.res.stringResource


// D, K.3: Gráfico de navegación (Routes)
sealed class LimaScreen(val route: String) {
    // Pantalla A: Lista de Categorías (Pantalla Principal)
    object Home : LimaScreen("home")

    // Pantalla B: Lista de Recomendaciones de una Categoría
    object CategoryList : LimaScreen("category_list/{categoryId}") {
        fun createRoute(categoryId: Int) = "category_list/$categoryId"
    }

    // Pantalla C: Detalle de una Recomendación
    object RecommendationDetail : LimaScreen("recommendation_detail/{recommendationId}") {
        fun createRoute(recommendationId: Int) = "recommendation_detail/$recommendationId"
    }
}

// H: Definición de Clases de Tamaño de Ventana para Diseño Adaptable
data class WindowSize(
    val widthSizeClass: WindowWidthSizeClass
)

enum class LimaContentType {
    COMPACT, // Pantalla de lista y detalle separada (Típicamente móvil)
    DUAL_PANE // Pantalla de lista y detalle lado a lado (Típicamente tablet/desktop)
}

// Componente para la navegación adaptable (NavigationRail)
@Composable
fun LimaNavigationRail(
    categories: List<Category>,
    onCategorySelected: (Int) -> Unit,
    currentCategoryId: Int,
    modifier: Modifier = Modifier
) {
    NavigationRail(
        modifier = modifier.fillMaxHeight(),
        containerColor = MaterialTheme.colorScheme.inverseOnSurface
    ) {
        categories.forEach { category ->
            NavigationRailItem(
                icon = {
                    Icon(
                        imageVector = category.icon,
                        contentDescription = stringResource(id = category.name)
                    )
                },
                label = { Text(stringResource(id = category.name)) },
                selected = category.id == currentCategoryId,
                onClick = { onCategorySelected(category.id) }
            )
        }
    }
}
