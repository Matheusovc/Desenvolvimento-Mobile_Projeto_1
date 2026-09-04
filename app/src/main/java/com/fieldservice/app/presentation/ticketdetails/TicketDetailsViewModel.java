package com.fieldservice.app.presentation.ticketdetails;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.fieldservice.app.domain.model.Ticket;
import com.fieldservice.app.domain.model.TicketStatus;
import com.fieldservice.app.domain.repository.TicketRepository;
import com.fieldservice.app.presentation.UiState;

import java.util.List;

/**
 * Expõe o chamado selecionado (derivado da fonte reativa do repositório, de modo que
 * avançar o status atualize a tela sozinho) e o estado de "atualizando".
 */
public class TicketDetailsViewModel extends ViewModel {

    private final TicketRepository ticketRepository;
    private final String ticketId;

    private final LiveData<UiState<Ticket>> uiState;
    private final MutableLiveData<Boolean> isUpdating = new MutableLiveData<>(false);

    public TicketDetailsViewModel(TicketRepository ticketRepository, String ticketId) {
        this.ticketRepository = ticketRepository;
        this.ticketId = ticketId;
        this.uiState = Transformations.map(ticketRepository.observeTickets(), this::findState);
    }

    private UiState<Ticket> findState(List<Ticket> tickets) {
        for (Ticket ticket : tickets) {
            if (ticket.getId().equals(ticketId)) {
                return UiState.success(ticket);
            }
        }
        return UiState.empty();
    }

    public LiveData<UiState<Ticket>> getUiState() {
        return uiState;
    }

    public LiveData<Boolean> getIsUpdating() {
        return isUpdating;
    }

    /** Move o chamado para o próximo status do fluxo (aceitar, deslocar, chegar, atender, finalizar). */
    public void advance(TicketStatus nextStatus) {
        isUpdating.setValue(true);
        ticketRepository.updateTicketStatus(ticketId, nextStatus, () -> isUpdating.setValue(false));
    }
}
