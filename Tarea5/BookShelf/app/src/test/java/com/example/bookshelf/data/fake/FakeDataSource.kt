package com.example.bookshelf.data.fake

import com.example.bookshelf.data.model.BookItem
import com.example.bookshelf.data.model.BookSearchResponse
import com.example.bookshelf.data.model.BookVolume
import com.example.bookshelf.data.model.ImageLinks
import com.example.bookshelf.data.model.VolumeInfo

object FakeDataSource {
    val bookItem1 = BookItem("id1")
    val bookItem2 = BookItem("id2")

    val volumeInfo1 = VolumeInfo("Title 1", ImageLinks("http://example.com/small1.jpg", "http://example.com/thumb1.jpg"))
    val volumeInfo2 = VolumeInfo("Title 2", ImageLinks("http://example.com/small2.jpg", "http://example.com/thumb2.jpg"))

    // --- CAMBIOS AQUÍ ---
    val fakeBookVolume1 = BookVolume(volumeInfo1) // Envolver
    val fakeBookVolume2 = BookVolume(volumeInfo2) // Envolver

    val fakeSearchResponse = BookSearchResponse(items = listOf(bookItem1, bookItem2))

    // El mapa ahora devuelve BookVolume
    val fakeBookDetailsMap = mapOf(
        "id1" to fakeBookVolume1,
        "id2" to fakeBookVolume2
    )
}