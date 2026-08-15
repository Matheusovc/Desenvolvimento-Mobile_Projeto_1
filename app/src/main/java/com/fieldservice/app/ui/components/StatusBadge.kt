package com.fieldservice.app.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.fieldservice.app.domain.model.TicketStatus
import com.fieldservice.app.ui.theme.StatusCancelled
import com.fieldservice.app.ui.theme.StatusCompleted
import com.fieldservice.app.ui.theme.StatusInProgress
import com.fieldservice.app.ui.theme.StatusNeutral

fun TicketStatus.label(): String = when (this) {
    TicketStatus.OPEN -> "Aberto"
    TicketStatus.ASSIGNED -> "Atribuído"
    TicketStatus.ACCEPTED -> "Aceito"
    TicketStatus.TRAVELING -> "A caminho"
    TicketStatus.ON_SITE -> "No local"
    TicketStatus.IN_PROGRESS -> "Em atendimento"
    TicketStatus.WAITING_CONFIRMATION -> "Aguardando confirmação"
    TicketStatus.COMPLETED -> "Concluído"
    TicketStatus.CANCELLED -> "Cancelado"
}

private fun TicketStatus.color(): Color = when (this) {
    TicketStatus.OPEN, TicketStatus.ASSIGNED -> StatusNeutral
    TicketStatus.ACCEPTED, TicketStatus.TRAVELING, TicketStatus.ON_SITE,
    TicketStatus.IN_PROGRESS, TicketStatus.WAITING_CONFIRMATION -> StatusInProgress
    TicketStatus.COMPLETED -> StatusCompleted
    TicketStatus.CANCELLED -> StatusCancelled
}

/** Selo de status do chamado, com rótulo textual (nunca só uma cor). */
@Composable
fun StatusBadge(status: TicketStatus, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(6.dp),
        color = status.color().copy(alpha = 0.12f),
        contentColor = status.color()
    ) {
        Text(
            text = status.label(),
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
        )
    }
}
