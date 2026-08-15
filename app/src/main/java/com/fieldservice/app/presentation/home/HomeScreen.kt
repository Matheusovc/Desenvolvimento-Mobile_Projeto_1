package com.fieldservice.app.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
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
import com.fieldservice.app.ui.components.LoadingState
import com.fieldservice.app.ui.components.TicketCard

@Composable
fun HomeScreen(
    onTicketClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel: HomeViewModel = viewModel(
        factory = viewModelFactory {
            initializer { HomeViewModel(AppContainer.ticketRepository, AppContainer.authRepository) }
        }
    )
    val uiState by viewModel.uiState.collectAsState()

    if (uiState.isLoading) {
        LoadingState(modifier = modifier)
        return
    }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Olá, ${uiState.technicianFirstName} 👋",
                style = MaterialTheme.typography.headlineSmall
            )
        }

        item {
            Text(
                text = "Resumo do dia",
                style = MaterialTheme.typography.titleMedium
            )
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                SummaryCard(count = uiState.pendingCount, label = "Chamados pendentes", modifier = Modifier.weight(1f))
                SummaryCard(count = uiState.inProgressCount, label = "Em atendimento", modifier = Modifier.weight(1f))
                SummaryCard(count = uiState.completedCount, label = "Concluídos", modifier = Modifier.weight(1f))
            }
        }

        if (uiState.priorityTickets.isNotEmpty()) {
            item {
                Text(
                    text = "Chamados prioritários",
                    style = MaterialTheme.typography.titleMedium
                )
            }
            items(uiState.priorityTickets, key = { it.id }) { ticket ->
                TicketCard(ticket = ticket, onClick = { onTicketClick(ticket.id) })
            }
        }
    }
}

@Composable
private fun SummaryCard(count: Int, label: String, modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(text = count.toString(), style = MaterialTheme.typography.headlineMedium)
            Text(text = label, style = MaterialTheme.typography.bodySmall)
        }
    }
}
