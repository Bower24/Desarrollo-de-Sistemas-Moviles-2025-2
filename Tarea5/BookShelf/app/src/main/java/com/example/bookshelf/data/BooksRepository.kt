package com.example.bookshelf.data

import com.example.bookshelf.data.network.BooksApiService
import com.example.bookshelf.model.Book // <-- Importa el NUEVO modelo de UI

// La interfaz ahora devuelve una lista de nuestro modelo de UI
interface BooksRepository {
    suspend fun getBooks(query: String): List<Book>
}

class NetworkBooksRepository(private val booksApiService: BooksApiService) : BooksRepository {

    // La implementación también devuelve la lista del modelo de UI
    override suspend fun getBooks(query: String): List<Book> {
        val searchResponse = booksApiService.searchBooks(query)
        val books = mutableListOf<Book>() // <-- Lista del NUEVO modelo 'Book'

        searchResponse.items?.let { items ->
            for (item in items) {
                try {
                    item.id?.let { id ->
                        val bookVolume = booksApiService.getBookInfo(id)

                        // --- LÓGICA DE MAPEO ---
                        // Solo si el libro devuelto tiene un ID y volumeInfo
                        if (bookVolume.id != null && bookVolume.volumeInfo != null) {
                            books.add(
                                Book(
                                    id = bookVolume.id, // ID (no nulo)
                                    title = bookVolume.volumeInfo.title ?: "Sin título", // Título (con valor predeterminado)
                                    imageUrl = bookVolume.volumeInfo.imageLinks?.httpsThumbnail // URL segura (puede ser nula)
                                )
                            )
                        }
                    }
                } catch (e: Exception) {
                    println("Error fetching details for book ID ${item.id}: ${e.message}")
                }
            }
        }
        return books
    }
}