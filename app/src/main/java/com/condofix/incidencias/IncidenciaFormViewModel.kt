package com.condofix.incidencias

import androidx.lifecycle.ViewModel
import com.condofix.incidencias.model.Campo
import com.condofix.incidencias.model.IncidenciaFormUi
import com.condofix.incidencias.model.Prioridad
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class IncidenciaFormViewModel : ViewModel() {
    private val _ui = MutableStateFlow(IncidenciaFormUi())
    val ui: StateFlow<IncidenciaFormUi> = _ui.asStateFlow()

    fun onTextoCambia(campo: Campo, valor: String) = _ui.update { it.conTexto(campo, valor) }
    fun onPrioridad(prioridad: Prioridad) = _ui.update { it.conPrioridad(prioridad) }
    fun onCampoTocado(campo: Campo) = _ui.update { it.marcarTocado(campo) }
    fun onLimpiar() = _ui.update { it.limpiar() }
    fun onGuardar() = _ui.update { it.guardar() }
}
