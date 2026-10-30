package com.condofix.incidencias.lista.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.condofix.incidencias.lista.model.Estado
import com.condofix.incidencias.lista.model.Incidencia
import com.condofix.incidencias.lista.model.Prioridad
import com.condofix.incidencias.ui.theme.EstadoCerrada
import com.condofix.incidencias.ui.theme.EstadoEnProceso
import com.condofix.incidencias.ui.theme.EstadoPendiente
import com.condofix.incidencias.ui.theme.PrioridadAlta
import com.condofix.incidencias.ui.theme.PrioridadBaja
import com.condofix.incidencias.ui.theme.PrioridadMedia

fun Prioridad.color(): Color = when (this) {
    Prioridad.ALTA -> PrioridadAlta
    Prioridad.MEDIA -> PrioridadMedia
    Prioridad.BAJA -> PrioridadBaja
}

fun Estado.color(): Color = when (this) {
    Estado.PENDIENTE -> EstadoPendiente
    Estado.EN_PROCESO -> EstadoEnProceso
    Estado.CERRADA -> EstadoCerrada
}

@Composable
private fun Etiqueta(texto: String, color: Color, modifier: Modifier = Modifier) {
    Text(
        text = texto,
        color = color,
        style = MaterialTheme.typography.labelSmall,
        modifier = modifier
            .background(color.copy(alpha = 0.15f), RoundedCornerShape(50))
            .padding(horizontal = 10.dp, vertical = 4.dp),
    )
}

/** Priority Badge del Lab 02: chip de color según urgencia. */
@Composable
fun PriorityBadge(prioridad: Prioridad, modifier: Modifier = Modifier) =
    Etiqueta(prioridad.etiqueta.uppercase(), prioridad.color(), modifier)

@Composable
fun EstadoChip(estado: Estado, modifier: Modifier = Modifier) =
    Etiqueta(estado.etiqueta, estado.color(), modifier)

/** KPI Card del Lab 02: número grande y etiqueta debajo. */
@Composable
fun KpiCard(etiqueta: String, valor: Int, color: Color, modifier: Modifier = Modifier) {
    Card(modifier = modifier, elevation = CardDefaults.cardElevation(1.dp)) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(valor.toString(), color = color, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            Text(etiqueta, style = MaterialTheme.typography.labelSmall)
        }
    }
}

/**
 * Composable reutilizable que dibuja UN registro. No sabe nada de la lista ni del ViewModel:
 * recibe el dato y avisa el toque con [onClick].
 */
@Composable
fun TicketCard(incidencia: Incidencia, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth().clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(2.dp),
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("#${incidencia.id}", color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.labelMedium)
                Spacer(Modifier.weight(1f))
                PriorityBadge(incidencia.prioridad)
            }
            Text(
                text = incidencia.titulo,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Text(incidencia.ubicacion, style = MaterialTheme.typography.bodySmall)
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                EstadoChip(incidencia.estado)
                Text(
                    text = incidencia.tecnico?.let { "Asignado a $it" } ?: "Sin asignar",
                    style = MaterialTheme.typography.bodySmall,
                )
                Spacer(Modifier.weight(1f))
                Text(incidencia.reportado, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}
