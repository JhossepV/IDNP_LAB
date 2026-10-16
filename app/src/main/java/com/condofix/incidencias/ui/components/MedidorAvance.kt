package com.condofix.incidencias.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.condofix.incidencias.model.IncidenciaFormUi
import com.condofix.incidencias.model.escalaUrgencia
import com.condofix.incidencias.model.textoAvance
import com.condofix.incidencias.model.textoUrgencia
import com.condofix.incidencias.ui.theme.AzulCondoFix
import com.condofix.incidencias.ui.theme.PrioridadBaja

private const val ANGULO_INICIAL = -90f
private const val ESCALA_MAXIMA = 1.15f

/**
 * Anillo de avance con un círculo central:
 * el arco refleja cuántos campos son válidos y el círculo la prioridad elegida.
 */
@Composable
fun MedidorAvance(ui: IncidenciaFormUi, modifier: Modifier = Modifier) {
    val progreso by animateFloatAsState(
        targetValue = ui.progreso,
        animationSpec = tween(durationMillis = 600, easing = FastOutSlowInEasing),
        label = "progreso",
    )
    val escala by animateFloatAsState(
        targetValue = escalaUrgencia(ui.prioridad),
        animationSpec = spring(dampingRatio = Spring.DampingRatioLowBouncy, stiffness = Spring.StiffnessLow),
        label = "escala",
    )
    val colorCirculo by animateColorAsState(
        targetValue = ui.prioridad?.color() ?: Color.Gray,
        animationSpec = tween(durationMillis = 400),
        label = "colorCirculo",
    )
    val colorArco by animateColorAsState(
        targetValue = if (ui.esValido) PrioridadBaja else AzulCondoFix,
        animationSpec = tween(durationMillis = 400),
        label = "colorArco",
    )
    val colorPista = MaterialTheme.colorScheme.outlineVariant

    val resumen = "${ui.camposValidos} de ${ui.totalCampos} campos válidos"

    Card(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Canvas(
                modifier = Modifier
                    .size(120.dp)
                    .semantics { contentDescription = "$resumen. ${textoUrgencia(ui.prioridad)}" },
            ) {
                val grosor = 12.dp.toPx()
                val diametro = size.minDimension - grosor
                val esquina = Offset(grosor / 2, grosor / 2)
                val tamanoArco = Size(diametro, diametro)
                val trazo = Stroke(width = grosor, cap = StrokeCap.Round)

                drawArc(
                    color = colorPista,
                    startAngle = ANGULO_INICIAL,
                    sweepAngle = 360f,
                    useCenter = false,
                    topLeft = esquina,
                    size = tamanoArco,
                    style = trazo,
                )
                // Con el extremo redondeado, un arco de 0° se vería como un punto.
                if (progreso > 0.001f) {
                    drawArc(
                        color = colorArco,
                        startAngle = ANGULO_INICIAL,
                        sweepAngle = 360f * progreso,
                        useCenter = false,
                        topLeft = esquina,
                        size = tamanoArco,
                        style = trazo,
                    )
                }

                val radioMaximo = (diametro / 2 - grosor) * 0.9f
                drawCircle(
                    color = colorCirculo,
                    // El rebote del spring puede pasarse de 1f; se limita para no tocar el anillo.
                    radius = radioMaximo * escala.coerceIn(0f, ESCALA_MAXIMA),
                    center = center,
                )
            }

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Text("Avance del reporte", style = MaterialTheme.typography.titleSmall)
                Text(resumen, style = MaterialTheme.typography.bodyMedium)
                Text(textoAvance(ui), style = MaterialTheme.typography.bodySmall)
                Text(textoUrgencia(ui.prioridad), style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}
