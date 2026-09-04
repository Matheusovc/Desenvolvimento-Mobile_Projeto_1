package com.fieldservice.app.domain.repository

import com.fieldservice.app.domain.model.Ticket
import kotlinx.coroutines.flow.Flow

/**
 * Fonte única de chamados para a camada de apresentação.
 * Hoje é implementada por [com.fieldservice.app.data.repository.MockTicketRepository];
 * no futuro, uma implementação baseada em Retrofit poderá substituí-la sem alterar nenhuma tela.
 */
interface TicketRepository {
    fun observeTickets(): Flow<List<Ticket>>
    suspend fun getTicketById(id: String): Ticket?
    suspend fun acceptTicket(id: String)
}
