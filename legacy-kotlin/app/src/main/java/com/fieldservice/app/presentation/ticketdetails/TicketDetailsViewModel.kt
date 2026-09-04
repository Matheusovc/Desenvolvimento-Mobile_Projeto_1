package com.fieldservice.app.presentation.ticketdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fieldservice.app.domain.model.Ticket
import com.fieldservice.app.domain.repository.TicketRepository
import com.fieldservice.app.presentation.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TicketDetailsViewModel(
    private val ticketRepository: TicketRepository,
    private val ticketId: String
) : ViewModel() {

    val uiState: StateFlow<UiState<Ticket>> = ticketRepository.observeTickets()
        .map { tickets -> tickets.find { it.id == ticketId } }
        .map { ticket -> if (ticket != null) UiState.Success(ticket) else UiState.Empty }
        .catch { error -> emit(UiState.Error(error.message ?: "Não foi possível carregar o chamado")) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), UiState.Loading)

    private val _isAccepting = MutableStateFlow(false)
    val isAccepting: StateFlow<Boolean> = _isAccepting

    fun acceptTicket() {
        viewModelScope.launch {
            _isAccepting.value = true
            ticketRepository.acceptTicket(ticketId)
            _isAccepting.value = false
        }
    }
}
