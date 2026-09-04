package com.fieldservice.app.data.repository

import com.fieldservice.app.domain.model.TicketStatus
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class MockTicketRepositoryTest {

    @Test
    fun `initial ticket list is not empty`() = runTest {
        val repository = MockTicketRepository()

        val tickets = repository.observeTickets().value

        assertTrue(tickets.isNotEmpty())
    }

    @Test
    fun `getTicketById returns null for unknown id`() = runTest {
        val repository = MockTicketRepository()

        val ticket = repository.getTicketById("does-not-exist")

        assertEquals(null, ticket)
    }

    @Test
    fun `acceptTicket moves ticket from ASSIGNED to ACCEPTED`() = runTest {
        val repository = MockTicketRepository()
        val assignedTicket = repository.observeTickets().value.first { it.status == TicketStatus.ASSIGNED }

        repository.acceptTicket(assignedTicket.id)

        val updated = repository.getTicketById(assignedTicket.id)
        assertEquals(TicketStatus.ACCEPTED, updated?.status)
        assertFalse(updated?.status == TicketStatus.ASSIGNED)
    }
}
