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
import com.fieldservice.app.domain.model.Priority
import com.fieldservice.app.ui.theme.PriorityCritical
import com.fieldservice.app.ui.theme.PriorityHigh
import com.fieldservice.app.ui.theme.PriorityLow
import com.fieldservice.app.ui.theme.PriorityMedium

private fun Priority.label(): String = when (this) {
    Priority.LOW -> "Baixa"
    Priority.MEDIUM -> "Média"
    Priority.HIGH -> "Alta"
    Priority.CRITICAL -> "Crítica"
}

private fun Priority.color(): Color = when (this) {
    Priority.LOW -> PriorityLow
    Priority.MEDIUM -> PriorityMedium
    Priority.HIGH -> PriorityHigh
    Priority.CRITICAL -> PriorityCritical
}

/** Selo de prioridade do chamado. Não depende só da cor: o texto identifica o nível. */
@Composable
fun PriorityBadge(priority: Priority, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(6.dp),
        color = priority.color().copy(alpha = 0.12f),
        contentColor = priority.color()
    ) {
        Text(
            text = priority.label(),
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
        )
    }
}
