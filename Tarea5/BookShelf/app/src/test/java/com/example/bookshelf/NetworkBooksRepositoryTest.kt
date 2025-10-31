package com.example.bookshelf

import com.example.bookshelf.data.NetworkBooksRepository
import com.example.bookshelf.data.fake.FakeBooksApiService
import com.example.bookshelf.data.fake.FakeDataSource
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class NetworkBooksRepositoryTest {

    @Test
    fun networkBooksRepository_getBooks_verifyBookList() = runTest {
        // Arrange
        val repository = NetworkBooksRepository(
            booksApiService = FakeBooksApiService()
        )

        // --- CAMBIO AQUÍ ---
        // La lista esperada son solo los VolumeInfo, no los BookVolume
        val expectedBooks = listOf(
            FakeDataSource.volumeInfo1,
            FakeDataSource.volumeInfo2
        )

        // Act
        val result = repository.getBooks("kotlin")

        // Assert
        assertEquals(expectedBooks, result)
    }
}