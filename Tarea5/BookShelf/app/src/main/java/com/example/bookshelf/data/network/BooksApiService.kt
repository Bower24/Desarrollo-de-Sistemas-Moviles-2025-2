package com.example.bookshelf.data.network

import com.example.bookshelf.data.model.BookSearchResponse
import com.example.bookshelf.data.model.BookVolume
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BooksApiService {
    /**
     * Realiza una búsqueda de libros.
     * @param query El término de búsqueda.
     */
    @GET("volumes")
    suspend fun searchBooks(@Query("q") query: String): BookSearchResponse

    /**
     * Obtiene la información detallada de un libro por su ID.
     * @param volumeId El ID del volumen/libro.
     */
    @GET("volumes/{volumeId}")
    suspend fun getBookInfo(@Path("volumeId") volumeId: String): BookVolume
}