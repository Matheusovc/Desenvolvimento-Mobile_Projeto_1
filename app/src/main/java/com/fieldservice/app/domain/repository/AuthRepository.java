package com.fieldservice.app.domain.repository;

import com.fieldservice.app.domain.model.Technician;

/**
 * Abstrai a autenticação do técnico. A implementação mock valida credenciais fixas;
 * futuramente será trocada por uma implementação que fala com uma API real.
 */
public interface AuthRepository {

    interface LoginCallback {
        void onSuccess(Technician technician);
        void onError(String message);
    }

    void login(String email, String password, LoginCallback callback);

    void logout();

    Technician getLoggedInTechnician();
}
