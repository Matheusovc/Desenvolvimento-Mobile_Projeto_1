package com.fieldservice.app.presentation.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.fieldservice.app.data.AppContainer
import com.fieldservice.app.ui.components.FieldServiceButton
import com.fieldservice.app.ui.components.FieldServiceTextField

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel: LoginViewModel = viewModel(
        factory = viewModelFactory {
            initializer { LoginViewModel(AppContainer.authRepository) }
        }
    )
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.isLoginSuccessful) {
        if (uiState.isLoginSuccessful) onLoginSuccess()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "FieldService",
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = "Gestão de atendimentos em campo",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(40.dp))

        FieldServiceTextField(
            value = uiState.email,
            onValueChange = viewModel::onEmailChange,
            label = "E-mail",
            keyboardType = KeyboardType.Email,
            isError = uiState.errorMessage != null
        )

        Spacer(modifier = Modifier.height(12.dp))

        FieldServiceTextField(
            value = uiState.password,
            onValueChange = viewModel::onPasswordChange,
            label = "Senha",
            isPassword = true,
            isPasswordVisible = uiState.isPasswordVisible,
            onTogglePasswordVisibility = viewModel::onTogglePasswordVisibility,
            isError = uiState.errorMessage != null
        )

        uiState.errorMessage?.let { message ->
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = message,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        FieldServiceButton(
            text = "Entrar",
            onClick = viewModel::login,
            isLoading = uiState.isLoading,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
