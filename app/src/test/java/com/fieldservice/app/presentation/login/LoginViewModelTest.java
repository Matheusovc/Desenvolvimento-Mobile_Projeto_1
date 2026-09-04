package com.fieldservice.app.presentation.login;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.fieldservice.app.data.repository.MockAuthRepository;

import org.junit.Rule;
import org.junit.Test;

public class LoginViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Test
    public void loginWithValidMockCredentialsSucceeds() {
        LoginViewModel viewModel = new LoginViewModel(new MockAuthRepository());
        viewModel.onEmailChange("tecnico@fieldservice.com");
        viewModel.onPasswordChange("123456");

        viewModel.login();

        assertTrue(viewModel.getUiState().getValue().isLoginSuccessful());
        assertNull(viewModel.getUiState().getValue().getErrorMessage());
    }

    @Test
    public void loginWithInvalidCredentialsShowsErrorAndDoesNotNavigate() {
        LoginViewModel viewModel = new LoginViewModel(new MockAuthRepository());
        viewModel.onEmailChange("errado@fieldservice.com");
        viewModel.onPasswordChange("senha-errada");

        viewModel.login();

        assertEquals(false, viewModel.getUiState().getValue().isLoginSuccessful());
        assertEquals("E-mail ou senha inválidos", viewModel.getUiState().getValue().getErrorMessage());
    }

    @Test
    public void loginWithBlankFieldsDoesNotCallRepository() {
        LoginViewModel viewModel = new LoginViewModel(new MockAuthRepository());

        viewModel.login();

        assertEquals("Informe e-mail e senha", viewModel.getUiState().getValue().getErrorMessage());
    }
}
