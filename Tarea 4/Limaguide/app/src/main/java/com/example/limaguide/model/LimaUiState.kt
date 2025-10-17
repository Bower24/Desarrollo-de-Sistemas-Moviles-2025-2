package com.example.limaguide.model


import com.example.limaguide.data.Category
import com.example.limaguide.data.Place

/**
 * Data class que representa el estado completo de la UI para la app.
 */
data class LimaUiState(
    val currentCategory: Category? = null,
    val recommendations: List<Place> = emptyList(),
    val currentPlace: Place? = null
)
