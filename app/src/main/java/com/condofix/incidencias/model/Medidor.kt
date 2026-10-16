package com.condofix.incidencias.model

/** Tamaño relativo (0f..1f) del círculo central según la prioridad elegida. */
fun escalaUrgencia(prioridad: Prioridad?): Float = when (prioridad) {
    Prioridad.ALTA -> 1.00f
    Prioridad.MEDIA -> 0.65f
    Prioridad.BAJA -> 0.35f
    null -> 0.15f
}

fun textoAvance(ui: IncidenciaFormUi): String =
    if (ui.esValido) {
        "Reporte completo: listo para guardar."
    } else {
        "Faltan: ${ui.camposPendientes.joinToString { it.etiqueta }}."
    }

fun textoUrgencia(prioridad: Prioridad?): String = when (prioridad) {
    Prioridad.ALTA -> "Urgencia alta: el círculo llega a su tamaño máximo."
    Prioridad.MEDIA -> "Urgencia media: el círculo ocupa dos tercios."
    Prioridad.BAJA -> "Urgencia baja: el círculo se mantiene pequeño."
    null -> "Elige una prioridad para ver la urgencia."
}
