package com.condofix.incidencias.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.condofix.incidencias.IncidenciaFormViewModel
import com.condofix.incidencias.model.Campo
import com.condofix.incidencias.model.Incidencia
import com.condofix.incidencias.model.IncidenciaFormUi
import com.condofix.incidencias.model.Prioridad
import com.condofix.incidencias.ui.components.CampoFormulario
import com.condofix.incidencias.ui.components.EtiquetaPrioridad
import com.condofix.incidencias.ui.components.MedidorAvance
import com.condofix.incidencias.ui.components.SelectorPrioridad
import com.condofix.incidencias.validation.Reglas

@Composable
fun NuevaIncidenciaRoute(viewModel: IncidenciaFormViewModel = viewModel()) {
    val ui by viewModel.ui.collectAsState()
    NuevaIncidenciaScreen(
        ui = ui,
        onTextoCambia = viewModel::onTextoCambia,
        onPrioridad = viewModel::onPrioridad,
        onCampoTocado = viewModel::onCampoTocado,
        onLimpiar = viewModel::onLimpiar,
        onGuardar = viewModel::onGuardar,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NuevaIncidenciaScreen(
    ui: IncidenciaFormUi,
    onTextoCambia: (Campo, String) -> Unit,
    onPrioridad: (Prioridad) -> Unit,
    onCampoTocado: (Campo) -> Unit,
    onLimpiar: () -> Unit,
    onGuardar: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(ui.ultimoRegistro) {
        ui.ultimoRegistro?.let { snackbarHostState.showSnackbar("Incidencia #${it.id} registrada") }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text("Nueva incidencia") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                ),
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            MedidorAvance(ui = ui, modifier = Modifier.padding(bottom = 8.dp))

            CampoFormulario(
                etiqueta = "Título de la incidencia",
                valor = ui.titulo,
                error = ui.errorVisible(Campo.TITULO),
                onValorChange = { onTextoCambia(Campo.TITULO, it) },
                onPerdioFoco = { onCampoTocado(Campo.TITULO) },
                contador = contador(ui.titulo, Reglas.TITULO_MAX),
            )
            CampoFormulario(
                etiqueta = "Ubicación (torre · piso · apto)",
                valor = ui.ubicacion,
                error = ui.errorVisible(Campo.UBICACION),
                onValorChange = { onTextoCambia(Campo.UBICACION, it) },
                onPerdioFoco = { onCampoTocado(Campo.UBICACION) },
            )
            CampoFormulario(
                etiqueta = "Teléfono de contacto",
                valor = ui.telefono,
                error = ui.errorVisible(Campo.TELEFONO),
                onValorChange = { onTextoCambia(Campo.TELEFONO, it) },
                onPerdioFoco = { onCampoTocado(Campo.TELEFONO) },
                teclado = KeyboardType.Phone,
                contador = contador(ui.telefono, Reglas.TELEFONO_LONGITUD),
            )
            CampoFormulario(
                etiqueta = "Descripción",
                valor = ui.descripcion,
                error = ui.errorVisible(Campo.DESCRIPCION),
                onValorChange = { onTextoCambia(Campo.DESCRIPCION, it) },
                onPerdioFoco = { onCampoTocado(Campo.DESCRIPCION) },
                lineas = 3,
                contador = contador(ui.descripcion, Reglas.DESCRIPCION_MAX),
            )

            SelectorPrioridad(
                seleccion = ui.prioridad,
                error = ui.errorVisible(Campo.PRIORIDAD),
                onSeleccion = onPrioridad,
            )

            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                OutlinedButton(onClick = onLimpiar, modifier = Modifier.weight(1f)) {
                    Text("Limpiar")
                }
                Button(onClick = onGuardar, enabled = ui.esValido, modifier = Modifier.weight(1f)) {
                    Text("Guardar")
                }
            }

            if (ui.registradas.isNotEmpty()) {
                Text(
                    text = "Registradas en esta sesión (${ui.registradas.size})",
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.padding(top = 20.dp, bottom = 4.dp),
                )
                ui.registradas.forEach { incidencia ->
                    IncidenciaRegistrada(incidencia, Modifier.padding(vertical = 4.dp))
                }
            }
        }
    }
}

@Composable
private fun IncidenciaRegistrada(incidencia: Incidencia, modifier: Modifier = Modifier) {
    Card(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text("#${incidencia.id} · ${incidencia.titulo}", style = MaterialTheme.typography.bodyMedium)
                Text(incidencia.ubicacion, style = MaterialTheme.typography.bodySmall)
            }
            EtiquetaPrioridad(incidencia.prioridad)
        }
    }
}

private fun contador(texto: String, maximo: Int) = "${texto.trim().length}/$maximo"
