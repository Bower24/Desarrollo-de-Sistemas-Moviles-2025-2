package com.example.limaguide.UI1

import androidx.lifecycle.ViewModel
import com.example.limaguide.data.LocalDataProvider
import com.example.limaguide.model.LimaUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LimaViewModel : ViewModel() {

    // Estado de la UI, privado y mutable
    private val _uiState = MutableStateFlow(LimaUiState())
    // Estado de la UI, público e inmutable
    val uiState: StateFlow<LimaUiState> = _uiState.asStateFlow()

    /**
     * Actualiza la categoría seleccionada y la lista de recomendaciones correspondientes.
     */
    fun updateCurrentCategory(categoryId: Int) {
        val category = LocalDataProvider.allCategories.find { it.id == categoryId }
        val recommendations = LocalDataProvider.getRecommendationsForCategory(categoryId)

        _uiState.update { currentState ->
            currentState.copy(
                currentCategory = category,
                recommendations = recommendations,
                // Al cambiar de categoría, seleccionamos el primer lugar por defecto
                currentPlace = recommendations.firstOrNull()
            )
        }
    }

    /**
     * Actualiza el lugar recomendado seleccionado actualmente.
     */
    fun updateCurrentPlace(placeId: Int) {
        val place = uiState.value.recommendations.find { it.id == placeId }
        _uiState.update { currentState ->
            currentState.copy(
                currentPlace = place
            )
        }
    }
}