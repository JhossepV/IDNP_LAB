package com.condofix.incidencias.lista

import androidx.lifecycle.ViewModel
import com.condofix.incidencias.lista.model.FiltroEstado
import com.condofix.incidencias.lista.model.ListaIncidenciasUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ListaIncidenciasViewModel : ViewModel() {
    private val _ui = MutableStateFlow(ListaIncidenciasUi())
    val ui: StateFlow<ListaIncidenciasUi> = _ui.asStateFlow()

    fun onFiltro(f: FiltroEstado) = _ui.update { it.conFiltro(f) }
    fun onSeleccionar(id: Int) = _ui.update { it.seleccionar(id) }
    fun onCerrarDetalle() = _ui.update { it.cerrarDetalle() }
}
