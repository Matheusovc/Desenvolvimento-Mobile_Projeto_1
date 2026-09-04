package com.fieldservice.app.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fieldservice.app.domain.model.Priority
import com.fieldservice.app.domain.model.Ticket
import com.fieldservice.app.domain.repository.AuthRepository
import com.fieldservice.app.domain.repository.TicketRepository
import com.fieldservice.app.presentation.tickets.TicketFilter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

data class HomeUiState(
    val isLoading: Boolean = true,
    val technicianFirstName: String = "",
    val pendingCount: Int = 0,
    val inProgressCount: Int = 0,
    val completedCount: Int = 0,
    val priorityTickets: List<Ticket> = emptyList(),
    val errorMessage: String? = null
)

private val NOT_FINISHED_STATUSES = TicketFilter.PENDING_STATUSES + TicketFilter.IN_PROGRESS_STATUSES

class HomeViewModel(
    ticketRepository: TicketRepository,
    authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        HomeUiState(technicianFirstName = authRepository.getLoggedInTechnician()?.name?.substringBefore(" ").orEmpty())
    )
    val uiState: StateFlow<HomeUiState> = _uiState

    init {
        ticketRepository.observeTickets()
            .onEach { tickets ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    pendingCount = tickets.count { TicketFilter.PENDING.matches(it.status) },
                    inProgressCount = tickets.count { TicketFilter.IN_PROGRESS.matches(it.status) },
                    completedCount = tickets.count { TicketFilter.COMPLETED.matches(it.status) },
                    priorityTickets = tickets
                        .filter { it.status in NOT_FINISHED_STATUSES }
                        .filter { it.priority == Priority.HIGH || it.priority == Priority.CRITICAL }
                        .sortedByDescending { it.priority }
                        .take(3),
                    errorMessage = null
                )
            }
            .catch { error ->
                _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = error.message)
            }
            .launchIn(viewModelScope)
    }
}
