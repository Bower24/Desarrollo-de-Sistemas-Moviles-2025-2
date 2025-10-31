package com.example.bookshelf.model

/**
 * Un modelo de datos simple que representa un libro para la capa de UI.
 */
data class Book(
    val id: String,
    val title: String,
    val imageUrl: String? // Usaremos la URL HTTPS segura
)

