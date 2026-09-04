package com.fieldservice.app.presentation.tickets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fieldservice.app.domain.model.Ticket
import com.fieldservice.app.domain.repository.TicketRepository
import com.fieldservice.app.presentation.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class TicketsViewModel(ticketRepository: TicketRepository) : ViewModel() {

    private val _selectedFilter = MutableStateFlow(TicketFilter.ALL)
    val selectedFilter: StateFlow<TicketFilter> = _selectedFilter

    val uiState: StateFlow<UiState<List<Ticket>>> = combine(
        ticketRepository.observeTickets(),
        _selectedFilter
    ) { tickets, filter ->
        val filtered = tickets.filter { filter.matches(it.status) }
        if (filtered.isEmpty()) UiState.Empty else UiState.Success(filtered)
    }
        .catch { error -> emit(UiState.Error(error.message ?: "Não foi possível carregar os chamados")) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), UiState.Loading)

    fun onFilterSelected(filter: TicketFilter) {
        _selectedFilter.value = filter
    }
}
