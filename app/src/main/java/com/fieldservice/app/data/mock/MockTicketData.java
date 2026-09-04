package com.fieldservice.app.data.mock;

import com.fieldservice.app.domain.model.Priority;
import com.fieldservice.app.domain.model.Ticket;
import com.fieldservice.app.domain.model.TicketStatus;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * MOCK: chamados de exemplo usados enquanto não existe uma API real.
 * Serve apenas para desenvolvimento/demonstração da primeira versão do app.
 */
public final class MockTicketData {

    private MockTicketData() {
    }

    private static Date daysAgo(int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -days);
        return calendar.getTime();
    }

    public static List<Ticket> initialTickets() {
        List<Ticket> tickets = new ArrayList<>();

        tickets.add(new Ticket(
                "1028",
                "#1028",
                "Servidor indisponível",
                "Servidor não inicializa após queda de energia.",
                "Empresa XYZ",
                "Brasília - DF",
                Priority.HIGH,
                TicketStatus.ASSIGNED,
                daysAgo(0)
        ));
        tickets.add(new Ticket(
                "1029",
                "#1029",
                "Falha de conexão de rede",
                "Estações de trabalho perdem conexão com a rede local intermitentemente.",
                "Empresa ABC",
                "São Paulo - SP",
                Priority.MEDIUM,
                TicketStatus.ACCEPTED,
                daysAgo(1)
        ));
        tickets.add(new Ticket(
                "1030",
                "#1030",
                "Impressora indisponível",
                "Impressora do setor financeiro não liga.",
                "Empresa Delta",
                "Belo Horizonte - MG",
                Priority.LOW,
                TicketStatus.COMPLETED,
                daysAgo(3)
        ));
        tickets.add(new Ticket(
                "1031",
                "#1031",
                "Sistema de backup falhando",
                "Rotina de backup noturno falhou nas últimas três execuções.",
                "Empresa Omega",
                "Curitiba - PR",
                Priority.CRITICAL,
                TicketStatus.OPEN,
                daysAgo(0)
        ));
        tickets.add(new Ticket(
                "1032",
                "#1032",
                "Instalação de novo equipamento",
                "Instalação e configuração de nova estação de trabalho.",
                "Empresa Prime",
                "Porto Alegre - RS",
                Priority.MEDIUM,
                TicketStatus.IN_PROGRESS,
                daysAgo(2)
        ));
        tickets.add(new Ticket(
                "1033",
                "#1033",
                "Manutenção preventiva",
                "Manutenção preventiva trimestral do parque de máquinas.",
                "Empresa Nova",
                "Recife - PE",
                Priority.LOW,
                TicketStatus.COMPLETED,
                daysAgo(5)
        ));
        tickets.add(new Ticket(
                "1034",
                "#1034",
                "Rede Wi-Fi instável",
                "Sinal de Wi-Fi cai constantemente no segundo andar do escritório.",
                "Empresa Zenith",
                "Brasília - DF",
                Priority.HIGH,
                TicketStatus.TRAVELING,
                daysAgo(0)
        ));

        return tickets;
    }
}
