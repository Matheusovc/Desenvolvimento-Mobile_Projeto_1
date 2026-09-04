package com.fieldservice.app.presentation.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.fieldservice.app.data.AppContainer
import com.fieldservice.app.ui.components.FieldServiceButton

@Composable
fun ProfileScreen(
    onLogout: () -> Unit,
    modifier: Modifier = Modifier
) {
    val technician = remember { AppContainer.authRepository.getLoggedInTechnician() }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(text = "Perfil", style = MaterialTheme.typography.headlineSmall)
        Text(text = technician?.name.orEmpty(), style = MaterialTheme.typography.titleMedium)
        Text(
            text = technician?.email.orEmpty(),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        FieldServiceButton(
            text = "Sair",
            onClick = {
                AppContainer.authRepository.logout()
                onLogout()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp)
        )
    }
}
