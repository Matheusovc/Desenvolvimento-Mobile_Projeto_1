package com.fieldservice.app.presentation

/**
 * Estado genérico para telas que carregam uma lista/entidade de uma fonte assíncrona
 * (hoje o mock, futuramente a API). Mantém Composables sem `if/else` espalhado.
 */
sealed interface UiState<out T> {
    data object Loading : UiState<Nothing>
    data class Success<T>(val data: T) : UiState<T>
    data class Error(val message: String) : UiState<Nothing>
    data object Empty : UiState<Nothing>
}
