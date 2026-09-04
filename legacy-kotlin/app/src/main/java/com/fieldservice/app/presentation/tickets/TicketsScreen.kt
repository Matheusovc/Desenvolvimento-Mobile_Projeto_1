package com.fieldservice.app.presentation.tickets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
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
import com.fieldservice.app.presentation.UiState
import com.fieldservice.app.ui.components.EmptyState
import com.fieldservice.app.ui.components.ErrorState
import com.fieldservice.app.ui.components.LoadingState
import com.fieldservice.app.ui.components.TicketCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicketsScreen(
    onTicketClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel: TicketsViewModel = viewModel(
        factory = viewModelFactory {
            initializer { TicketsViewModel(AppContainer.ticketRepository) }
        }
    )
    val uiState by viewModel.uiState.collectAsState()
    val selectedFilter by viewModel.selectedFilter.collectAsState()

    Column(modifier = modifier.fillMaxSize()) {
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(TicketFilter.entries) { filter ->
                FilterChip(
                    selected = filter == selectedFilter,
                    onClick = { viewModel.onFilterSelected(filter) },
                    label = { Text(filter.label) }
                )
            }
        }

        when (val state = uiState) {
            is UiState.Loading -> LoadingState()
            is UiState.Error -> ErrorState(message = state.message)
            is UiState.Empty -> EmptyState(message = "Nenhum chamado encontrado para este filtro")
            is UiState.Success -> LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(state.data, key = { it.id }) { ticket ->
                    TicketCard(ticket = ticket, onClick = { onTicketClick(ticket.id) })
                }
            }
        }
    }
}
