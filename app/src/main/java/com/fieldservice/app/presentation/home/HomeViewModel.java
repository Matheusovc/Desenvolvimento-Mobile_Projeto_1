package com.fieldservice.app.presentation.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.fieldservice.app.domain.model.Priority;
import com.fieldservice.app.domain.model.Ticket;
import com.fieldservice.app.domain.model.Technician;
import com.fieldservice.app.domain.repository.AuthRepository;
import com.fieldservice.app.domain.repository.TicketRepository;
import com.fieldservice.app.presentation.tickets.TicketFilter;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {

    private static final int MAX_PRIORITY_TICKETS = 3;

    private final LiveData<HomeUiState> uiState;

    public HomeViewModel(TicketRepository ticketRepository, AuthRepository authRepository) {
        String firstName = firstNameOf(authRepository.getLoggedInTechnician());
        uiState = Transformations.map(
                ticketRepository.observeTickets(),
                tickets -> buildState(tickets, firstName)
        );
    }

    public LiveData<HomeUiState> getUiState() {
        return uiState;
    }

    private static String firstNameOf(Technician technician) {
        if (technician == null || technician.getName() == null) {
            return "";
        }
        String name = technician.getName();
        int spaceIndex = name.indexOf(' ');
        return spaceIndex >= 0 ? name.substring(0, spaceIndex) : name;
    }

    private static HomeUiState buildState(List<Ticket> tickets, String firstName) {
        int pending = 0;
        int inProgress = 0;
        int completed = 0;
        List<Ticket> priorityTickets = new ArrayList<>();

        for (Ticket ticket : tickets) {
            if (TicketFilter.PENDING.matches(ticket.getStatus())) {
                pending++;
            }
            if (TicketFilter.IN_PROGRESS.matches(ticket.getStatus())) {
                inProgress++;
            }
            if (TicketFilter.COMPLETED.matches(ticket.getStatus())) {
                completed++;
            }

            boolean notFinished = TicketFilter.PENDING.matches(ticket.getStatus())
                    || TicketFilter.IN_PROGRESS.matches(ticket.getStatus());
            boolean highPriority = ticket.getPriority() == Priority.HIGH
                    || ticket.getPriority() == Priority.CRITICAL;
            if (notFinished && highPriority) {
                priorityTickets.add(ticket);
            }
        }

        priorityTickets.sort((a, b) -> b.getPriority().compareTo(a.getPriority()));
        if (priorityTickets.size() > MAX_PRIORITY_TICKETS) {
            priorityTickets = priorityTickets.subList(0, MAX_PRIORITY_TICKETS);
        }

        return new HomeUiState(firstName, pending, inProgress, completed, priorityTickets);
    }
}
