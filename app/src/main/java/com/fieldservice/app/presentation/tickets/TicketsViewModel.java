package com.fieldservice.app.presentation.tickets;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fieldservice.app.domain.model.Ticket;
import com.fieldservice.app.domain.repository.TicketRepository;
import com.fieldservice.app.presentation.UiState;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class TicketsViewModel extends ViewModel {

    private final MutableLiveData<TicketFilter> selectedFilter = new MutableLiveData<>(TicketFilter.ALL);
    private final MediatorLiveData<UiState<List<Ticket>>> uiState = new MediatorLiveData<>();
    // Contagem de chamados por filtro, exibida nos chips (ex.: "Todos 7").
    private final MutableLiveData<Map<TicketFilter, Integer>> filterCounts = new MutableLiveData<>();

    private List<Ticket> latestTickets = Collections.emptyList();
    private TicketFilter latestFilter = TicketFilter.ALL;

    public TicketsViewModel(TicketRepository ticketRepository) {
        uiState.setValue(UiState.loading());

        uiState.addSource(ticketRepository.observeTickets(), tickets -> {
            latestTickets = tickets;
            updateCounts();
            recompute();
        });
        uiState.addSource(selectedFilter, filter -> {
            latestFilter = filter;
            recompute();
        });
    }

    public LiveData<UiState<List<Ticket>>> getUiState() {
        return uiState;
    }

    public LiveData<TicketFilter> getSelectedFilter() {
        return selectedFilter;
    }

    public LiveData<Map<TicketFilter, Integer>> getFilterCounts() {
        return filterCounts;
    }

    public void onFilterSelected(TicketFilter filter) {
        selectedFilter.setValue(filter);
    }

    /** Reprocessa a lista atual (usado pelo botão "Tentar novamente"). */
    public void retry() {
        updateCounts();
        recompute();
    }

    private void updateCounts() {
        Map<TicketFilter, Integer> counts = new EnumMap<>(TicketFilter.class);
        for (TicketFilter filter : TicketFilter.values()) {
            int count = 0;
            for (Ticket ticket : latestTickets) {
                if (filter.matches(ticket.getStatus())) {
                    count++;
                }
            }
            counts.put(filter, count);
        }
        filterCounts.setValue(counts);
    }

    private void recompute() {
        List<Ticket> filtered = new ArrayList<>();
        for (Ticket ticket : latestTickets) {
            if (latestFilter.matches(ticket.getStatus())) {
                filtered.add(ticket);
            }
        }
        uiState.setValue(filtered.isEmpty() ? UiState.empty() : UiState.success(filtered));
    }
}
