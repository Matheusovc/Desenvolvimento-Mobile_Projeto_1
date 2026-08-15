package com.fieldservice.app.domain.repository

import com.fieldservice.app.domain.model.Technician

/**
 * Abstrai a autenticação do técnico. A implementação mock valida credenciais fixas;
 * futuramente será trocada por uma implementação que fala com uma API real.
 */
interface AuthRepository {
    suspend fun login(email: String, password: String): Result<Technician>
    fun logout()
    fun getLoggedInTechnician(): Technician?
}
