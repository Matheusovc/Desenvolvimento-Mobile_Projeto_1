package com.fieldservice.app.presentation.ticketdetails;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import com.fieldservice.app.R;
import com.fieldservice.app.domain.model.TicketStatus;

/**
 * Próxima ação disponível para o técnico, de acordo com o status atual do chamado.
 * Modela o fluxo de atendimento como uma máquina de estados simples:
 * ASSIGNED → ACCEPTED → TRAVELING → ON_SITE → IN_PROGRESS → COMPLETED.
 */
public final class TicketAction {

    @StringRes
    public final int labelRes;
    public final TicketStatus nextStatus;

    private TicketAction(@StringRes int labelRes, TicketStatus nextStatus) {
        this.labelRes = labelRes;
        this.nextStatus = nextStatus;
    }

    /** Retorna a ação principal para o status informado, ou {@code null} se não há próximo passo. */
    @Nullable
    public static TicketAction forStatus(TicketStatus status) {
        switch (status) {
            case ASSIGNED:
                return new TicketAction(R.string.action_accept_ticket, TicketStatus.ACCEPTED);
            case ACCEPTED:
                return new TicketAction(R.string.action_start_travel, TicketStatus.TRAVELING);
            case TRAVELING:
                return new TicketAction(R.string.action_register_arrival, TicketStatus.ON_SITE);
            case ON_SITE:
                return new TicketAction(R.string.action_start_service, TicketStatus.IN_PROGRESS);
            case IN_PROGRESS:
                return new TicketAction(R.string.action_finish_service, TicketStatus.COMPLETED);
            default:
                return null;
        }
    }
}
