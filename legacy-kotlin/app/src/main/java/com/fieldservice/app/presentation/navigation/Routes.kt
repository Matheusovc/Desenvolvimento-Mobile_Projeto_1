package com.fieldservice.app.presentation.navigation

/** Rotas de navegação centralizadas: nenhuma outra parte do app deve escrever essas strings à mão. */
object Routes {
    const val LOGIN = "login"
    const val HOME = "home"
    const val TICKETS = "tickets"
    const val PROFILE = "profile"

    const val TICKET_ID_ARG = "ticketId"
    const val TICKET_DETAILS = "ticket/{$TICKET_ID_ARG}"

    fun ticketDetails(ticketId: String) = "ticket/$ticketId"
}
