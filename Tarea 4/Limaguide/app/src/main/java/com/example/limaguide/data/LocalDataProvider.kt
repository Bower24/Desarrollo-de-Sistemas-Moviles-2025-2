package com.example.limaguide.data

import com.example.limaguide.R
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Coffee
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Park
import androidx.compose.material.icons.filled.ShoppingBag

/**
 * Un objeto que provee los datos locales (hardcodeados) para la aplicación.
 * Esto simula una capa de datos.
 */
object LocalDataProvider {
    val allCategories = listOf(
        Category(1, R.string.category_coffee_shops, Icons.Filled.Coffee),
        Category(2, R.string.category_restaurants, Icons.Filled.Restaurant),
        Category(3, R.string.category_parks, Icons.Filled.Park),
        Category(4, R.string.category_malls, Icons.Filled.ShoppingBag)
    )

    private val allRecommendations = listOf(
        // Cafeterías
        Place(101, 1, R.string.coffee_1_name, R.string.coffee_1_desc, R.drawable.neira),
        Place(102, 1, R.string.coffee_2_name, R.string.coffee_2_desc, R.drawable.pukupuku),
        Place(103, 1, R.string.coffee_3_name, R.string.coffee_3_desc, R.drawable.colonia),

        // Restaurantes
        Place(201, 2, R.string.resto_1_name, R.string.resto_1_desc, R.drawable.central),
        Place(202, 2, R.string.resto_2_name, R.string.resto_2_desc, R.drawable.maido),
        Place(203, 2, R.string.resto_3_name, R.string.resto_3_desc, R.drawable.isolina),
        Place(204, 2, R.string.resto_4_name, R.string.resto_4_desc, R.drawable.elmercado),

        // Parques
        Place(301, 3, R.string.park_1_name, R.string.park_1_desc, R.drawable.elolivar),
        Place(302, 3, R.string.park_2_name, R.string.park_2_desc, R.drawable.kennedy),
        Place(303, 3, R.string.park_3_name, R.string.park_3_desc, R.drawable.circuito),

        // Centros Comerciales
        Place(401, 4, R.string.mall_1_name, R.string.mall_1_desc, R.drawable.larcomar),
        Place(402, 4, R.string.mall_2_name, R.string.mall_2_desc, R.drawable.jockey),
        Place(403, 4, R.string.mall_3_name, R.string.mall_3_desc, R.drawable.salaverry)
    )

    /**
     * Retorna la lista de recomendaciones para una categoría específica.
     */
    fun getRecommendationsForCategory(categoryId: Int): List<Place> {
        return allRecommendations.filter { it.categoryId == categoryId }
    }
}
