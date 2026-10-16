package com.condofix.incidencias.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.condofix.incidencias.model.Prioridad
import com.condofix.incidencias.ui.theme.PrioridadAlta
import com.condofix.incidencias.ui.theme.PrioridadBaja
import com.condofix.incidencias.ui.theme.PrioridadMedia

fun Prioridad.color(): Color = when (this) {
    Prioridad.ALTA -> PrioridadAlta
    Prioridad.MEDIA -> PrioridadMedia
    Prioridad.BAJA -> PrioridadBaja
}

@Composable
fun EtiquetaPrioridad(prioridad: Prioridad, modifier: Modifier = Modifier) {
    Text(
        text = prioridad.etiqueta.uppercase(),
        color = prioridad.color(),
        style = MaterialTheme.typography.labelSmall,
        modifier = modifier
            .background(prioridad.color().copy(alpha = 0.15f), RoundedCornerShape(50))
            .padding(horizontal = 10.dp, vertical = 4.dp),
    )
}

/**
 * Campo de texto sin estado propio. [onPerdioFoco] se dispara cuando el usuario
 * sale del campo después de haberlo enfocado, para empezar a mostrar errores.
 */
@Composable
fun CampoFormulario(
    etiqueta: String,
    valor: String,
    error: String?,
    onValorChange: (String) -> Unit,
    onPerdioFoco: () -> Unit,
    modifier: Modifier = Modifier,
    teclado: KeyboardType = KeyboardType.Text,
    lineas: Int = 1,
    contador: String = "",
) {
    var tuvoFoco by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = valor,
        onValueChange = onValorChange,
        label = { Text(etiqueta) },
        isError = error != null,
        supportingText = { Text(error ?: contador) },
        keyboardOptions = KeyboardOptions(keyboardType = teclado),
        singleLine = lineas == 1,
        minLines = lineas,
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged { estado ->
                if (estado.isFocused) {
                    tuvoFoco = true
                } else if (tuvoFoco) {
                    onPerdioFoco()
                }
            },
    )
}

@Composable
fun SelectorPrioridad(
    seleccion: Prioridad?,
    error: String?,
    onSeleccion: (Prioridad) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier) {
        Text("Prioridad", style = MaterialTheme.typography.labelLarge)
        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Prioridad.entries.forEach { prioridad ->
                FilterChip(
                    selected = seleccion == prioridad,
                    onClick = { onSeleccion(prioridad) },
                    label = { Text(prioridad.etiqueta) },
                    modifier = Modifier.weight(1f),
                )
            }
        }
        if (error != null) {
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 4.dp, start = 4.dp),
            )
        }
    }
}
