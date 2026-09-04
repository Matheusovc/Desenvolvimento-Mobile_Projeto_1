package com.fieldservice.app.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.fieldservice.app.data.mock.MockTicketData;
import com.fieldservice.app.domain.model.Ticket;
import com.fieldservice.app.domain.model.TicketStatus;
import com.fieldservice.app.domain.repository.TicketRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementação mock de {@link TicketRepository}: mantém os chamados em memória.
 * Responde de forma síncrona (sem thread/handler) para não depender do runtime Android
 * em testes de unidade puros.
 */
public class MockTicketRepository implements TicketRepository {

    private final MutableLiveData<List<Ticket>> tickets =
            new MutableLiveData<>(MockTicketData.initialTickets());

    @Override
    public LiveData<List<Ticket>> observeTickets() {
        return tickets;
    }

    @Override
    public void getTicketById(String id, TicketCallback callback) {
        Ticket found = null;
        for (Ticket ticket : tickets.getValue()) {
            if (ticket.getId().equals(id)) {
                found = ticket;
                break;
            }
        }
        callback.onResult(found);
    }

    @Override
    public void acceptTicket(String id, OperationCallback callback) {
        updateTicketStatus(id, TicketStatus.ACCEPTED, callback);
    }

    @Override
    public void updateTicketStatus(String id, TicketStatus status, OperationCallback callback) {
        List<Ticket> updated = new ArrayList<>();
        for (Ticket ticket : tickets.getValue()) {
            if (ticket.getId().equals(id)) {
                updated.add(ticket.withStatus(status));
            } else {
                updated.add(ticket);
            }
        }
        tickets.setValue(updated);
        callback.onComplete();
    }
}
