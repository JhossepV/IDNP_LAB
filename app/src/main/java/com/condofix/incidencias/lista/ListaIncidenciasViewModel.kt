package com.condofix.incidencias.lista

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.condofix.incidencias.lista.data.PreferenciasRepository
import com.condofix.incidencias.lista.data.ajustesDataStore
import com.condofix.incidencias.lista.model.FiltroEstado
import com.condofix.incidencias.lista.model.ListaIncidenciasUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ListaIncidenciasViewModel(
    private val preferencias: PreferenciasRepository,
) : ViewModel() {
    private val _ui = MutableStateFlow(ListaIncidenciasUi())
    val ui: StateFlow<ListaIncidenciasUi> = _ui.asStateFlow()

    init {
        // DataStore es la fuente de verdad del filtro: cada valor leído actualiza el estado.
        viewModelScope.launch {
            preferencias.filtro.collect { f -> _ui.update { it.conFiltro(f) } }
        }
    }

    /** Solo escribe la preferencia; el nuevo estado llega por el flujo. */
    fun onFiltro(f: FiltroEstado) {
        viewModelScope.launch { preferencias.guardarFiltro(f) }
    }

    fun onSeleccionar(id: Int) = _ui.update { it.seleccionar(id) }
    fun onCerrarDetalle() = _ui.update { it.cerrarDetalle() }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as Application
                ListaIncidenciasViewModel(PreferenciasRepository(app.ajustesDataStore))
            }
        }
    }
}
