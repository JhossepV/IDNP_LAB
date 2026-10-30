package com.condofix.incidencias.lista.model

enum class Prioridad(val etiqueta: String) { ALTA("Alta"), MEDIA("Media"), BAJA("Baja") }

enum class Estado(val etiqueta: String) {
    PENDIENTE("Pendiente"),
    EN_PROCESO("En proceso"),
    CERRADA("Cerrada"),
}

/**
 * Un registro de la lista. `id` es la clave estable que usa LazyColumn.
 * Atributos visibles: id, titulo, ubicacion, descripcion, prioridad, estado, tecnico y reportado.
 */
data class Incidencia(
    val id: Int,
    val titulo: String,
    val ubicacion: String,
    val descripcion: String,
    val prioridad: Prioridad,
    val estado: Estado,
    val tecnico: String?,
    val reportado: String,
)
