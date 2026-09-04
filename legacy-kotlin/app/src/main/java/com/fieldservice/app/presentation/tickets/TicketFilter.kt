package com.fieldservice.app.presentation.tickets

import com.fieldservice.app.domain.model.TicketStatus

/**
 * Agrupamento de status em categorias visíveis ao técnico (filtros da tela de Chamados
 * e contadores da Home). É uma decisão de apresentação, por isso não vive em `domain`.
 */
enum class TicketFilter(val label: String) {
    ALL("Todos"),
    PENDING("Pendentes"),
    IN_PROGRESS("Em atendimento"),
    COMPLETED("Concluídos");

    fun matches(status: TicketStatus): Boolean = when (this) {
        ALL -> true
        PENDING -> status in PENDING_STATUSES
        IN_PROGRESS -> status in IN_PROGRESS_STATUSES
        COMPLETED -> status in COMPLETED_STATUSES
    }

    companion object {
        val PENDING_STATUSES = setOf(
            TicketStatus.OPEN,
            TicketStatus.ASSIGNED,
            TicketStatus.ACCEPTED,
            TicketStatus.TRAVELING
        )
        val IN_PROGRESS_STATUSES = setOf(
            TicketStatus.ON_SITE,
            TicketStatus.IN_PROGRESS,
            TicketStatus.WAITING_CONFIRMATION
        )
        val COMPLETED_STATUSES = setOf(
            TicketStatus.COMPLETED,
            TicketStatus.CANCELLED
        )
    }
}
