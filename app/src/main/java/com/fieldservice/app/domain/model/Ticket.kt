package com.fieldservice.app.domain.model

import java.util.Date

/**
 * Representa um chamado de assistência técnica.
 * Campos suficientes para a primeira versão; a estrutura pode crescer
 * (ex.: peças utilizadas, evidências, técnico responsável detalhado) sem quebrar quem já a consome.
 */
data class Ticket(
    val id: String,
    val number: String,
    val title: String,
    val description: String,
    val customerName: String,
    val address: String,
    val priority: Priority,
    val status: TicketStatus,
    val createdAt: Date
)
