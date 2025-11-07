package com.example.flightsearch.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.flightsearch.data.Airport
import com.example.flightsearch.data.AppDatabase
import com.example.flightsearch.data.Favorite
import com.example.flightsearch.data.UserPreferencesRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class FlightViewModel(
    private val database: AppDatabase,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(FlightUiState())
    val uiState: StateFlow<FlightUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            userPreferencesRepository.searchQuery.collect { query ->
                _uiState.update { it.copy(searchQuery = query) }
                if (query.isNotEmpty()) {
                    database.flightDao().getAirportsByQuery(query).collect { suggestions ->
                        _uiState.update { it.copy(suggestions = suggestions) }
                    }
                }
            }
        }
    }

    fun onQueryChange(query: String) {
        _uiState.update { it.copy(searchQuery = query, selectedAirport = null) }
        viewModelScope.launch {
            userPreferencesRepository.saveSearchQuery(query)
        }
    }

    fun onAirportSelected(airport: Airport) {
        _uiState.update { it.copy(selectedAirport = airport, searchQuery = airport.iataCode) }
    }

    fun getDestinationsStream(departureCode: String): Flow<List<Airport>> {
        return database.flightDao().getAirportsByQuery("").map { airports ->
            airports.filter { it.iataCode != departureCode }
        }
    }

    fun getFavoritesStream(): Flow<List<Favorite>> {
        return database.flightDao().getAllFavorites()
    }

    fun getFavoriteStream(departureCode: String, destinationCode: String): Flow<Favorite?> {
        return database.flightDao().getFavorite(departureCode, destinationCode)
    }

    fun toggleFavorite(departureCode: String, destinationCode: String) {
        viewModelScope.launch {
            val favorite = database.flightDao().getFavorite(departureCode, destinationCode).first()
            if (favorite == null) {
                database.flightDao().addFavorite(
                    Favorite(departureCode = departureCode, destinationCode = destinationCode)
                )
            } else {
                database.flightDao().removeFavorite(favorite)
            }
        }
    }
}

data class FlightUiState(
    val searchQuery: String = "",
    val suggestions: List<Airport> = emptyList(),
    val selectedAirport: Airport? = null,
)

// Factory para inyectar dependencias en el ViewModel
class FlightViewModelFactory(
    private val database: AppDatabase,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FlightViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FlightViewModel(database, userPreferencesRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}