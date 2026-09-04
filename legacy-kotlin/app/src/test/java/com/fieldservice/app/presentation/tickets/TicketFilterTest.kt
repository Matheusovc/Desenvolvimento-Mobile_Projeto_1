package com.fieldservice.app.presentation.tickets

import com.fieldservice.app.domain.model.TicketStatus
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class TicketFilterTest {

    @Test
    fun `ALL matches every status`() {
        TicketStatus.entries.forEach { status ->
            assertTrue(TicketFilter.ALL.matches(status))
        }
    }

    @Test
    fun `PENDING matches only open-like statuses`() {
        assertTrue(TicketFilter.PENDING.matches(TicketStatus.ASSIGNED))
        assertFalse(TicketFilter.PENDING.matches(TicketStatus.COMPLETED))
    }

    @Test
    fun `COMPLETED matches finished statuses only`() {
        assertTrue(TicketFilter.COMPLETED.matches(TicketStatus.COMPLETED))
        assertTrue(TicketFilter.COMPLETED.matches(TicketStatus.CANCELLED))
        assertFalse(TicketFilter.COMPLETED.matches(TicketStatus.IN_PROGRESS))
    }
}
