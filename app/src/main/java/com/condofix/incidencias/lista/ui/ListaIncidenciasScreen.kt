package com.condofix.incidencias.lista.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.condofix.incidencias.lista.ListaIncidenciasViewModel
import com.condofix.incidencias.lista.model.Estado
import com.condofix.incidencias.lista.model.FiltroEstado
import com.condofix.incidencias.lista.model.Incidencia
import com.condofix.incidencias.lista.model.ListaIncidenciasUi
import com.condofix.incidencias.ui.theme.EstadoCerrada
import com.condofix.incidencias.ui.theme.EstadoEnProceso
import com.condofix.incidencias.ui.theme.EstadoPendiente

/** Contenedor con estado: conecta el ViewModel con la pantalla sin estado. */
@Composable
fun ListaIncidenciasRoute(vm: ListaIncidenciasViewModel = viewModel(factory = ListaIncidenciasViewModel.Factory)) {
    val ui by vm.ui.collectAsState()
    ListaIncidenciasScreen(
        ui = ui,
        onFiltro = vm::onFiltro,
        onSeleccionar = vm::onSeleccionar,
        onCerrarDetalle = vm::onCerrarDetalle,
    )
}

@Composable
fun ListaIncidenciasScreen(
    ui: ListaIncidenciasUi,
    onFiltro: (FiltroEstado) -> Unit,
    onSeleccionar: (Int) -> Unit,
    onCerrarDetalle: () -> Unit,
) {
    // Se crea aquí (fuera del if) para que la posición del scroll se conserve al abrir y cerrar el detalle.
    val listState = rememberLazyListState()
    LaunchedEffect(ui.filtro) { listState.scrollToItem(0) }

    val detalle = ui.seleccionada
    if (detalle != null) {
        BackHandler(onBack = onCerrarDetalle)
        DetalleIncidenciaScreen(detalle, onVolver = onCerrarDetalle)
    } else {
        PantallaLista(ui, listState, onFiltro, onSeleccionar)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PantallaLista(
    ui: ListaIncidenciasUi,
    listState: LazyListState,
    onFiltro: (FiltroEstado) -> Unit,
    onSeleccionar: (Int) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Incidencias (${ui.visibles.size})") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White,
                ),
            )
        },
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            // Row de KPIs: tres tarjetas con el mismo ancho
            Row(
                modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp, top = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                KpiCard("Pendientes", ui.contar(Estado.PENDIENTE), EstadoPendiente, Modifier.weight(1f))
                KpiCard("En proceso", ui.contar(Estado.EN_PROCESO), EstadoEnProceso, Modifier.weight(1f))
                KpiCard("Cerradas", ui.contar(Estado.CERRADA), EstadoCerrada, Modifier.weight(1f))
            }

            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(items = FiltroEstado.entries, key = { it.name }) { f ->
                    FilterChip(selected = ui.filtro == f, onClick = { onFiltro(f) }, label = { Text(f.etiqueta) })
                }
            }

            // La lista: solo se componen las tarjetas visibles. `key` = id estable del registro.
            LazyColumn(
                state = listState,
                modifier = Modifier.weight(1f).fillMaxWidth(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                items(items = ui.visibles, key = { it.id }) { incidencia ->
                    TicketCard(incidencia = incidencia, onClick = { onSeleccionar(incidencia.id) })
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleIncidenciaScreen(incidencia: Incidencia, onVolver: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Ticket #${incidencia.id}") },
                navigationIcon = {
                    IconButton(onClick = onVolver) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White,
                ),
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier.padding(padding).verticalScroll(rememberScrollState()).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Text(incidencia.titulo, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.SemiBold)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                PriorityBadge(incidencia.prioridad)
                EstadoChip(incidencia.estado)
            }
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Dato("Ubicación", incidencia.ubicacion)
                    Dato("Reportado", incidencia.reportado)
                    Dato("Técnico", incidencia.tecnico ?: "Sin asignar")
                    Dato("Descripción", incidencia.descripcion)
                }
            }
        }
    }
}

@Composable
private fun Dato(etiqueta: String, valor: String) {
    Column {
        Text(etiqueta, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.primary)
        Text(valor, style = MaterialTheme.typography.bodyLarge)
    }
}
