package com.fieldservice.app.domain.model

/**
 * Estágios possíveis de um chamado, do momento em que é aberto até a conclusão.
 * Centralizar isso em enum evita strings soltas representando status pelo app.
 */
enum class TicketStatus {
    OPEN,
    ASSIGNED,
    ACCEPTED,
    TRAVELING,
    ON_SITE,
    IN_PROGRESS,
    WAITING_CONFIRMATION,
    COMPLETED,
    CANCELLED
}
