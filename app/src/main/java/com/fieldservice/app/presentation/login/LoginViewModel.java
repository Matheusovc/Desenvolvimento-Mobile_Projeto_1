package com.fieldservice.app.presentation.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fieldservice.app.domain.model.Technician;
import com.fieldservice.app.domain.repository.AuthRepository;

public class LoginViewModel extends ViewModel {

    private final AuthRepository authRepository;
    private final MutableLiveData<LoginUiState> uiState = new MutableLiveData<>(LoginUiState.initial());

    public LoginViewModel(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public LiveData<LoginUiState> getUiState() {
        return uiState;
    }

    public void onEmailChange(String email) {
        uiState.setValue(uiState.getValue().withEmail(email));
    }

    public void onPasswordChange(String password) {
        uiState.setValue(uiState.getValue().withPassword(password));
    }

    public void onTogglePasswordVisibility() {
        uiState.setValue(uiState.getValue().withPasswordVisibleToggled());
    }

    public void login() {
        LoginUiState current = uiState.getValue();
        if (current.getEmail().trim().isEmpty() || current.getPassword().trim().isEmpty()) {
            uiState.setValue(current.withError("Informe e-mail e senha"));
            return;
        }

        uiState.setValue(current.withLoading());
        authRepository.login(current.getEmail(), current.getPassword(), new AuthRepository.LoginCallback() {
            @Override
            public void onSuccess(Technician technician) {
                uiState.setValue(uiState.getValue().withLoginSuccessful());
            }

            @Override
            public void onError(String message) {
                uiState.setValue(uiState.getValue().withError(message));
            }
        });
    }
}
