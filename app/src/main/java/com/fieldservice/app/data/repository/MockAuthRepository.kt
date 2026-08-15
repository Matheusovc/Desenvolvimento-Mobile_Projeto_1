package com.fieldservice.app.data.repository

import com.fieldservice.app.domain.model.Technician
import com.fieldservice.app.domain.repository.AuthRepository
import kotlinx.coroutines.delay

/**
 * Implementação mock de [AuthRepository]: valida contra uma única credencial fixa.
 * MOCK — não representa autenticação real e deve ser substituída por uma API futuramente.
 */
class MockAuthRepository : AuthRepository {

    private var loggedInTechnician: Technician? = null

    override suspend fun login(email: String, password: String): Result<Technician> {
        delay(NETWORK_DELAY_MS)
        return if (email.equals(MOCK_EMAIL, ignoreCase = true) && password == MOCK_PASSWORD) {
            val technician = Technician(id = "tech-1", name = "João Silva", email = MOCK_EMAIL)
            loggedInTechnician = technician
            Result.success(technician)
        } else {
            Result.failure(InvalidCredentialsException())
        }
    }

    override fun logout() {
        loggedInTechnician = null
    }

    override fun getLoggedInTechnician(): Technician? = loggedInTechnician

    private companion object {
        const val NETWORK_DELAY_MS = 600L
        const val MOCK_EMAIL = "tecnico@fieldservice.com"
        const val MOCK_PASSWORD = "123456"
    }
}

class InvalidCredentialsException : Exception("E-mail ou senha inválidos")
