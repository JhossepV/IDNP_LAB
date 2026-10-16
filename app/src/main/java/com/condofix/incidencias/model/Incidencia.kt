package com.condofix.incidencias.model

enum class Prioridad(val etiqueta: String) {
    ALTA("Alta"),
    MEDIA("Media"),
    BAJA("Baja"),
}

enum class Campo(val etiqueta: String) {
    TITULO("Título"),
    UBICACION("Ubicación"),
    TELEFONO("Teléfono"),
    DESCRIPCION("Descripción"),
    PRIORIDAD("Prioridad"),
}

data class Incidencia(
    val id: Int,
    val titulo: String,
    val ubicacion: String,
    val telefono: String,
    val descripcion: String,
    val prioridad: Prioridad,
)
