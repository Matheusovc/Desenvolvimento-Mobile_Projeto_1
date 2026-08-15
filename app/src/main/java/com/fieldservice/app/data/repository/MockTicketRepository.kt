package com.fieldservice.app.data.repository

import com.fieldservice.app.data.mock.MockTicketData
import com.fieldservice.app.domain.model.Ticket
import com.fieldservice.app.domain.model.TicketStatus
import com.fieldservice.app.domain.repository.TicketRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

/**
 * Implementação mock de [TicketRepository]: mantém os chamados em memória.
 * O pequeno delay simula uma chamada de rede para que a UI já lide corretamente
 * com o estado de carregamento quando a API real entrar em cena.
 */
class MockTicketRepository : TicketRepository {

    private val tickets: MutableStateFlow<List<Ticket>> =
        MutableStateFlow(MockTicketData.initialTickets())

    override fun observeTickets(): StateFlow<List<Ticket>> = tickets

    override suspend fun getTicketById(id: String): Ticket? {
        delay(NETWORK_DELAY_MS)
        return tickets.value.find { it.id == id }
    }

    override suspend fun acceptTicket(id: String) {
        delay(NETWORK_DELAY_MS)
        tickets.update { current ->
            current.map { ticket ->
                if (ticket.id == id) ticket.copy(status = TicketStatus.ACCEPTED) else ticket
            }
        }
    }

    private companion object {
        const val NETWORK_DELAY_MS = 400L
    }
}
