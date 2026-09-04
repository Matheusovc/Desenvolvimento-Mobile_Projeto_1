package com.fieldservice.app.data.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.fieldservice.app.domain.model.Ticket;
import com.fieldservice.app.domain.model.TicketStatus;
import com.fieldservice.app.domain.repository.TicketRepository;

import org.junit.Rule;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class MockTicketRepositoryTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Test
    public void initialTicketListIsNotEmpty() {
        MockTicketRepository repository = new MockTicketRepository();

        List<Ticket> tickets = repository.observeTickets().getValue();

        assertTrue(tickets != null && !tickets.isEmpty());
    }

    @Test
    public void getTicketByIdReturnsNullForUnknownId() {
        MockTicketRepository repository = new MockTicketRepository();
        AtomicReference<Ticket> result = new AtomicReference<>();

        repository.getTicketById("does-not-exist", result::set);

        assertNull(result.get());
    }

    @Test
    public void acceptTicketMovesTicketFromAssignedToAccepted() {
        MockTicketRepository repository = new MockTicketRepository();
        Ticket assignedTicket = findFirstAssigned(repository.observeTickets().getValue());

        repository.acceptTicket(assignedTicket.getId(), () -> {
        });

        AtomicReference<Ticket> updated = new AtomicReference<>();
        repository.getTicketById(assignedTicket.getId(), updated::set);

        assertEquals(TicketStatus.ACCEPTED, updated.get().getStatus());
        assertFalse(updated.get().getStatus() == TicketStatus.ASSIGNED);
    }

    private static Ticket findFirstAssigned(List<Ticket> tickets) {
        for (Ticket ticket : tickets) {
            if (ticket.getStatus() == TicketStatus.ASSIGNED) {
                return ticket;
            }
        }
        throw new IllegalStateException("Nenhum chamado ASSIGNED encontrado no mock");
    }
}
