package com.example.bookshelf.data.fake

import com.example.bookshelf.data.model.BookSearchResponse
import com.example.bookshelf.data.model.BookVolume // <-- Cambiar import
import com.example.bookshelf.data.model.VolumeInfo // (Este ya no se usa aquí)
import com.example.bookshelf.data.network.BooksApiService

class FakeBooksApiService : BooksApiService {
    override suspend fun searchBooks(query: String): BookSearchResponse {
        return FakeDataSource.fakeSearchResponse
    }

    // --- CAMBIO AQUÍ ---
    override suspend fun getBookInfo(volumeId: String): BookVolume { // <-- Tipo de retorno
        return FakeDataSource.fakeBookDetailsMap[volumeId]!!
    }
}