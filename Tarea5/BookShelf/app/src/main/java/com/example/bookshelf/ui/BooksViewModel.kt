package com.example.bookshelf.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.bookshelf.BookshelfApplication
import com.example.bookshelf.data.BooksRepository
import com.example.bookshelf.data.model.VolumeInfo
import kotlinx.coroutines.launch

import com.example.bookshelf.model.Book // <-- Importa el NUEVO modelo de UI
import kotlinx.coroutines.launch
import java.io.IOException

// El estado de éxito ahora contiene una List<Book>
sealed interface BooksUiState {
    data class Success(val books: List<Book>) : BooksUiState
    object Error : BooksUiState
    object Loading : BooksUiState
}

class BooksViewModel(private val booksRepository: BooksRepository) : ViewModel() {

    var booksUiState: BooksUiState by mutableStateOf(BooksUiState.Loading)
        private set

    init {
        getBooks()
    }

    fun getBooks(query: String = "kotlin") {
        viewModelScope.launch {
            booksUiState = BooksUiState.Loading
            booksUiState = try {
                // Esto funciona sin cambios, porque el repositorio ya devuelve List<Book>
                BooksUiState.Success(booksRepository.getBooks(query))
            } catch (e: IOException) {
                BooksUiState.Error
            }
        }
    }

    // Factory para crear el ViewModel con sus dependencias
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as BookshelfApplication)
                val booksRepository = application.container.booksRepository
                BooksViewModel(booksRepository = booksRepository)
            }
        }
    }
}