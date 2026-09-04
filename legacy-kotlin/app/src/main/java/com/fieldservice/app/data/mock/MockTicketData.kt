package com.fieldservice.app.data.mock

import com.fieldservice.app.domain.model.Priority
import com.fieldservice.app.domain.model.Ticket
import com.fieldservice.app.domain.model.TicketStatus
import java.util.Calendar
import java.util.Date

/**
 * MOCK: chamados de exemplo usados enquanto não existe uma API real.
 * Serve apenas para desenvolvimento/demonstração da primeira versão do app.
 */
object MockTicketData {

    private fun daysAgo(days: Int): Date {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, -days)
        return calendar.time
    }

    fun initialTickets(): List<Ticket> = listOf(
        Ticket(
            id = "1028",
            number = "#1028",
            title = "Servidor indisponível",
            description = "Servidor não inicializa após queda de energia.",
            customerName = "Empresa XYZ",
            address = "Brasília - DF",
            priority = Priority.HIGH,
            status = TicketStatus.ASSIGNED,
            createdAt = daysAgo(0)
        ),
        Ticket(
            id = "1029",
            number = "#1029",
            title = "Falha de conexão de rede",
            description = "Estações de trabalho perdem conexão com a rede local intermitentemente.",
            customerName = "Empresa ABC",
            address = "São Paulo - SP",
            priority = Priority.MEDIUM,
            status = TicketStatus.ACCEPTED,
            createdAt = daysAgo(1)
        ),
        Ticket(
            id = "1030",
            number = "#1030",
            title = "Impressora indisponível",
            description = "Impressora do setor financeiro não liga.",
            customerName = "Empresa Delta",
            address = "Belo Horizonte - MG",
            priority = Priority.LOW,
            status = TicketStatus.COMPLETED,
            createdAt = daysAgo(3)
        ),
        Ticket(
            id = "1031",
            number = "#1031",
            title = "Sistema de backup falhando",
            description = "Rotina de backup noturno falhou nas últimas três execuções.",
            customerName = "Empresa Omega",
            address = "Curitiba - PR",
            priority = Priority.CRITICAL,
            status = TicketStatus.OPEN,
            createdAt = daysAgo(0)
        ),
        Ticket(
            id = "1032",
            number = "#1032",
            title = "Instalação de novo equipamento",
            description = "Instalação e configuração de nova estação de trabalho.",
            customerName = "Empresa Prime",
            address = "Porto Alegre - RS",
            priority = Priority.MEDIUM,
            status = TicketStatus.IN_PROGRESS,
            createdAt = daysAgo(2)
        ),
        Ticket(
            id = "1033",
            number = "#1033",
            title = "Manutenção preventiva",
            description = "Manutenção preventiva trimestral do parque de máquinas.",
            customerName = "Empresa Nova",
            address = "Recife - PE",
            priority = Priority.LOW,
            status = TicketStatus.COMPLETED,
            createdAt = daysAgo(5)
        ),
        Ticket(
            id = "1034",
            number = "#1034",
            title = "Rede Wi-Fi instável",
            description = "Sinal de Wi-Fi cai constantemente no segundo andar do escritório.",
            customerName = "Empresa Zenith",
            address = "Brasília - DF",
            priority = Priority.HIGH,
            status = TicketStatus.TRAVELING,
            createdAt = daysAgo(0)
        )
    )
}
