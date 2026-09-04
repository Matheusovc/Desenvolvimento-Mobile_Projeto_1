package com.fieldservice.app.domain.repository;

import androidx.lifecycle.LiveData;

import com.fieldservice.app.domain.model.Ticket;
import com.fieldservice.app.domain.model.TicketStatus;

import java.util.List;

/**
 * Fonte única de chamados para a camada de apresentação.
 * Hoje é implementada por {@link com.fieldservice.app.data.repository.MockTicketRepository};
 * no futuro, uma implementação baseada em Retrofit poderá substituí-la sem alterar nenhuma tela.
 */
public interface TicketRepository {

    interface TicketCallback {
        void onResult(Ticket ticket);
    }

    interface OperationCallback {
        void onComplete();
    }

    LiveData<List<Ticket>> observeTickets();

    void getTicketById(String id, TicketCallback callback);

    void acceptTicket(String id, OperationCallback callback);

    /** Avança (ou muda) o chamado para um novo status do fluxo de atendimento. */
    void updateTicketStatus(String id, TicketStatus status, OperationCallback callback);
}
