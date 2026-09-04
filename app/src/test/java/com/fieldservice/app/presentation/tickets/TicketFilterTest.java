package com.fieldservice.app.presentation.tickets;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.fieldservice.app.domain.model.TicketStatus;

import org.junit.Test;

public class TicketFilterTest {

    @Test
    public void allMatchesEveryStatus() {
        for (TicketStatus status : TicketStatus.values()) {
            assertTrue(TicketFilter.ALL.matches(status));
        }
    }

    @Test
    public void pendingMatchesOnlyOpenLikeStatuses() {
        assertTrue(TicketFilter.PENDING.matches(TicketStatus.ASSIGNED));
        assertFalse(TicketFilter.PENDING.matches(TicketStatus.COMPLETED));
    }

    @Test
    public void completedMatchesFinishedStatusesOnly() {
        assertTrue(TicketFilter.COMPLETED.matches(TicketStatus.COMPLETED));
        assertTrue(TicketFilter.COMPLETED.matches(TicketStatus.CANCELLED));
        assertFalse(TicketFilter.COMPLETED.matches(TicketStatus.IN_PROGRESS));
    }
}
