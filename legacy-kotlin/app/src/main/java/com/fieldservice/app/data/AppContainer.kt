package com.fieldservice.app.data

import com.fieldservice.app.data.repository.MockAuthRepository
import com.fieldservice.app.data.repository.MockTicketRepository
import com.fieldservice.app.domain.repository.AuthRepository
import com.fieldservice.app.domain.repository.TicketRepository

/**
 * Provedor manual das dependências do app (sem Hilt, para manter a primeira versão simples).
 * As telas dependem apenas das interfaces [AuthRepository]/[TicketRepository]; trocar a
 * implementação mock por uma baseada em API significa mudar apenas este arquivo.
 */
object AppContainer {
    val authRepository: AuthRepository by lazy { MockAuthRepository() }
    val ticketRepository: TicketRepository by lazy { MockTicketRepository() }
}
