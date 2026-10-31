package com.condofix.incidencias.lista.model

import com.condofix.incidencias.lista.data.DatosPrueba

enum class FiltroEstado(val etiqueta: String, val estado: Estado?) {
    TODOS("Todos", null),
    PENDIENTES("Pendientes", Estado.PENDIENTE),
    EN_PROCESO("En proceso", Estado.EN_PROCESO),
    CERRADAS("Cerradas", Estado.CERRADA);

    companion object {
        /** Convierte el texto guardado en DataStore; si falta o no es válido, usa TODOS. */
        fun desdeNombre(nombre: String?): FiltroEstado =
            entries.firstOrNull { it.name == nombre } ?: TODOS
    }
}

/**
 * Estado inmutable de la pantalla de lista (estado elevado).
 * La UI solo lo dibuja; toda la lógica de filtrado y selección es pura y se prueba en JVM.
 */
data class ListaIncidenciasUi(
    val incidencias: List<Incidencia> = DatosPrueba.incidencias(),
    val filtro: FiltroEstado = FiltroEstado.TODOS,
    val seleccionadaId: Int? = null,
) {
    /** Lo que muestra la LazyColumn según el filtro activo (mantiene el orden original). */
    val visibles: List<Incidencia>
        get() = filtro.estado?.let { e -> incidencias.filter { it.estado == e } } ?: incidencias

    fun contar(estado: Estado): Int = incidencias.count { it.estado == estado }

    /** Incidencia abierta en el detalle, o null si se está viendo la lista. */
    val seleccionada: Incidencia?
        get() = incidencias.firstOrNull { it.id == seleccionadaId }

    fun conFiltro(f: FiltroEstado): ListaIncidenciasUi = copy(filtro = f)

    fun seleccionar(id: Int): ListaIncidenciasUi =
        copy(seleccionadaId = if (incidencias.any { it.id == id }) id else null)

    fun cerrarDetalle(): ListaIncidenciasUi = copy(seleccionadaId = null)
}
