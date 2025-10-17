package com.example.limaguide.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Clase de datos para una Categoría.
 */
data class Category(
    val id: Int,
    @StringRes val name: Int,
    val icon: ImageVector
)

/**
 * Clase de datos para un Lugar recomendado.
 */
data class Place(
    val id: Int,
    val categoryId: Int,
    @StringRes val name: Int,
    @StringRes val description: Int,
    @DrawableRes val image: Int
)
