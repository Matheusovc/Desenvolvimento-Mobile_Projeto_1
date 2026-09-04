package com.fieldservice.app.presentation.ticketdetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.fieldservice.app.data.AppContainer
import com.fieldservice.app.domain.model.TicketStatus
import com.fieldservice.app.presentation.UiState
import com.fieldservice.app.ui.components.EmptyState
import com.fieldservice.app.ui.components.ErrorState
import com.fieldservice.app.ui.components.FieldServiceButton
import com.fieldservice.app.ui.components.LoadingState
import com.fieldservice.app.ui.components.PriorityBadge
import com.fieldservice.app.ui.components.StatusBadge

@Composable
fun TicketDetailsScreen(
    ticketId: String,
    modifier: Modifier = Modifier
) {
    val viewModel: TicketDetailsViewModel = viewModel(
        factory = viewModelFactory {
            initializer { TicketDetailsViewModel(AppContainer.ticketRepository, ticketId) }
        }
    )
    val uiState by viewModel.uiState.collectAsState()
    val isAccepting by viewModel.isAccepting.collectAsState()

    when (val state = uiState) {
        is UiState.Loading -> LoadingState(modifier = modifier)
        is UiState.Empty -> EmptyState(message = "Chamado não encontrado", modifier = modifier)
        is UiState.Error -> ErrorState(message = state.message, modifier = modifier)
        is UiState.Success -> {
            val ticket = state.data
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(text = ticket.number, style = MaterialTheme.typography.headlineSmall)
                Text(text = ticket.customerName, style = MaterialTheme.typography.titleMedium)
                Text(text = ticket.title, style = MaterialTheme.typography.bodyLarge)

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    PriorityBadge(priority = ticket.priority)
                    StatusBadge(status = ticket.status)
                }

                DetailSection(label = "CLIENTE", value = ticket.customerName)
                DetailSection(label = "ENDEREÇO", value = ticket.address)
                DetailSection(label = "DESCRIÇÃO", value = ticket.description)

                if (ticket.status == TicketStatus.ASSIGNED) {
                    FieldServiceButton(
                        text = "ACEITAR CHAMADO",
                        onClick = viewModel::acceptTicket,
                        isLoading = isAccepting,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Composable
private fun DetailSection(label: String, value: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(2.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(text = value, style = MaterialTheme.typography.bodyMedium)
    }
}
