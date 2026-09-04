package com.fieldservice.app.presentation.login;

/** Estado imutável da tela de Login; cada mudança gera uma nova instância. */
public final class LoginUiState {

    private final String email;
    private final String password;
    private final boolean passwordVisible;
    private final boolean loading;
    private final String errorMessage;
    private final boolean loginSuccessful;

    private LoginUiState(
            String email,
            String password,
            boolean passwordVisible,
            boolean loading,
            String errorMessage,
            boolean loginSuccessful
    ) {
        this.email = email;
        this.password = password;
        this.passwordVisible = passwordVisible;
        this.loading = loading;
        this.errorMessage = errorMessage;
        this.loginSuccessful = loginSuccessful;
    }

    public static LoginUiState initial() {
        return new LoginUiState("", "", false, false, null, false);
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public boolean isPasswordVisible() {
        return passwordVisible;
    }

    public boolean isLoading() {
        return loading;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public boolean isLoginSuccessful() {
        return loginSuccessful;
    }

    public LoginUiState withEmail(String newEmail) {
        return new LoginUiState(newEmail, password, passwordVisible, loading, null, loginSuccessful);
    }

    public LoginUiState withPassword(String newPassword) {
        return new LoginUiState(email, newPassword, passwordVisible, loading, null, loginSuccessful);
    }

    public LoginUiState withPasswordVisibleToggled() {
        return new LoginUiState(email, password, !passwordVisible, loading, errorMessage, loginSuccessful);
    }

    public LoginUiState withError(String message) {
        return new LoginUiState(email, password, passwordVisible, false, message, false);
    }

    public LoginUiState withLoading() {
        return new LoginUiState(email, password, passwordVisible, true, null, false);
    }

    public LoginUiState withLoginSuccessful() {
        return new LoginUiState(email, password, passwordVisible, false, null, true);
    }
}
