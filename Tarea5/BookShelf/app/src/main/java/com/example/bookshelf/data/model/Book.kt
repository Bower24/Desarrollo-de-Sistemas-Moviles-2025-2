package com.example.bookshelf.data.model

// ... (BookSearchResponse y BookItem se mantienen como en la Idea 2)
data class BookSearchResponse(
    val items: List<BookItem>?
)

data class BookItem(
    val id: String?
)

// Modelo para la respuesta completa de /volumes/{id}
data class BookVolume(
    val id: String?, // <-- AÑADE ESTA LÍNEA (la API lo incluye aquí)
    val volumeInfo: VolumeInfo?
)

// Modelo para la información detallada de un volumen
data class VolumeInfo(
    val title: String?,
    val imageLinks: ImageLinks?
)

// Modelo para los enlaces de imágenes
data class ImageLinks(
    val smallThumbnail: String?,
    val thumbnail: String?
) {
    // Propiedad para obtener la URL segura (HTTPS)
    val httpsThumbnail: String?
        get() = thumbnail?.replace("http://", "https://")
}