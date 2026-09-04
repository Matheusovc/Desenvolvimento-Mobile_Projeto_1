package com.fieldservice.app.presentation.tickets;

import com.fieldservice.app.domain.model.TicketStatus;

import java.util.EnumSet;
import java.util.Set;

/**
 * Agrupamento de status em categorias visíveis ao técnico (filtros da tela de Chamados
 * e contadores da Home). É uma decisão de apresentação, por isso não vive em domain.
 */
public enum TicketFilter {
    ALL("Todos"),
    PENDING("Pendentes"),
    IN_PROGRESS("Em atendimento"),
    COMPLETED("Concluídos");

    public static final Set<TicketStatus> PENDING_STATUSES = EnumSet.of(
            TicketStatus.OPEN,
            TicketStatus.ASSIGNED,
            TicketStatus.ACCEPTED,
            TicketStatus.TRAVELING
    );

    public static final Set<TicketStatus> IN_PROGRESS_STATUSES = EnumSet.of(
            TicketStatus.ON_SITE,
            TicketStatus.IN_PROGRESS,
            TicketStatus.WAITING_CONFIRMATION
    );

    public static final Set<TicketStatus> COMPLETED_STATUSES = EnumSet.of(
            TicketStatus.COMPLETED,
            TicketStatus.CANCELLED
    );

    private final String label;

    TicketFilter(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public boolean matches(TicketStatus status) {
        switch (this) {
            case ALL:
                return true;
            case PENDING:
                return PENDING_STATUSES.contains(status);
            case IN_PROGRESS:
                return IN_PROGRESS_STATUSES.contains(status);
            case COMPLETED:
                return COMPLETED_STATUSES.contains(status);
            default:
                return false;
        }
    }
}
