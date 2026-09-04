package com.fieldservice.app.presentation.ticketdetails;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import com.fieldservice.app.domain.model.TicketStatus;

import org.junit.Test;

public class TicketActionTest {

    @Test
    public void flowAdvancesThroughEachAttendanceStep() {
        assertEquals(TicketStatus.ACCEPTED, TicketAction.forStatus(TicketStatus.ASSIGNED).nextStatus);
        assertEquals(TicketStatus.TRAVELING, TicketAction.forStatus(TicketStatus.ACCEPTED).nextStatus);
        assertEquals(TicketStatus.ON_SITE, TicketAction.forStatus(TicketStatus.TRAVELING).nextStatus);
        assertEquals(TicketStatus.IN_PROGRESS, TicketAction.forStatus(TicketStatus.ON_SITE).nextStatus);
        assertEquals(TicketStatus.COMPLETED, TicketAction.forStatus(TicketStatus.IN_PROGRESS).nextStatus);
    }

    @Test
    public void terminalStatusesHaveNoNextAction() {
        assertNull(TicketAction.forStatus(TicketStatus.COMPLETED));
        assertNull(TicketAction.forStatus(TicketStatus.CANCELLED));
        assertNull(TicketAction.forStatus(TicketStatus.WAITING_CONFIRMATION));
        assertNull(TicketAction.forStatus(TicketStatus.OPEN));
    }
}
