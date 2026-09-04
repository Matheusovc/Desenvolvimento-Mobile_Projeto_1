package com.fieldservice.app.presentation.home;

import com.fieldservice.app.domain.model.Ticket;

import java.util.Collections;
import java.util.List;

/** Estado da tela Home: resumo do dia e chamados prioritários. */
public final class HomeUiState {

    private final String technicianFirstName;
    private final int pendingCount;
    private final int inProgressCount;
    private final int completedCount;
    private final List<Ticket> priorityTickets;

    public HomeUiState(
            String technicianFirstName,
            int pendingCount,
            int inProgressCount,
            int completedCount,
            List<Ticket> priorityTickets
    ) {
        this.technicianFirstName = technicianFirstName;
        this.pendingCount = pendingCount;
        this.inProgressCount = inProgressCount;
        this.completedCount = completedCount;
        this.priorityTickets = Collections.unmodifiableList(priorityTickets);
    }

    public String getTechnicianFirstName() {
        return technicianFirstName;
    }

    public int getPendingCount() {
        return pendingCount;
    }

    public int getInProgressCount() {
        return inProgressCount;
    }

    public int getCompletedCount() {
        return completedCount;
    }

    public List<Ticket> getPriorityTickets() {
        return priorityTickets;
    }
}
