package com.fieldservice.app.data.repository;

import com.fieldservice.app.domain.model.Technician;
import com.fieldservice.app.domain.repository.AuthRepository;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementação mock de {@link AuthRepository}: valida contra credenciais fixas.
 * MOCK — não representa autenticação real e deve ser substituída por uma API futuramente.
 * Responde de forma síncrona (sem thread/handler) para não depender do runtime Android
 * em testes de unidade puros.
 */
public class MockAuthRepository implements AuthRepository {

    private static final String MOCK_PASSWORD = "123456";

    private static final Map<String, Technician> MOCK_TECHNICIANS = new HashMap<>();
    static {
        MOCK_TECHNICIANS.put(
                "tecnico@fieldservice.com",
                new Technician("tech-1", "João Silva", "tecnico@fieldservice.com"));
        MOCK_TECHNICIANS.put(
                "matheus@fieldservice.com",
                new Technician("tech-2", "Matheus Carvalho", "matheus@fieldservice.com"));
    }

    private Technician loggedInTechnician;

    @Override
    public void login(String email, String password, LoginCallback callback) {
        Technician technician = email == null ? null : MOCK_TECHNICIANS.get(email.toLowerCase());
        if (technician != null && MOCK_PASSWORD.equals(password)) {
            loggedInTechnician = technician;
            callback.onSuccess(loggedInTechnician);
        } else {
            callback.onError("E-mail ou senha inválidos");
        }
    }

    @Override
    public void logout() {
        loggedInTechnician = null;
    }

    @Override
    public Technician getLoggedInTechnician() {
        return loggedInTechnician;
    }
}
