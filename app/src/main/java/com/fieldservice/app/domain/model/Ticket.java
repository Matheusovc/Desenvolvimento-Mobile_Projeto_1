package com.fieldservice.app.domain.model;

import java.util.Date;

/**
 * Representa um chamado de assistência técnica.
 * Campos suficientes para a primeira versão; a estrutura pode crescer
 * (ex.: peças utilizadas, evidências, técnico responsável detalhado) sem quebrar quem já a consome.
 */
public final class Ticket {

    private final String id;
    private final String number;
    private final String title;
    private final String description;
    private final String customerName;
    private final String address;
    private final Priority priority;
    private final TicketStatus status;
    private final Date createdAt;

    public Ticket(
            String id,
            String number,
            String title,
            String description,
            String customerName,
            String address,
            Priority priority,
            TicketStatus status,
            Date createdAt
    ) {
        this.id = id;
        this.number = number;
        this.title = title;
        this.description = description;
        this.customerName = customerName;
        this.address = address;
        this.priority = priority;
        this.status = status;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getAddress() {
        return address;
    }

    public Priority getPriority() {
        return priority;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    /** Retorna uma cópia deste chamado com um novo status, preservando os demais campos. */
    public Ticket withStatus(TicketStatus newStatus) {
        return new Ticket(id, number, title, description, customerName, address, priority, newStatus, createdAt);
    }
}
