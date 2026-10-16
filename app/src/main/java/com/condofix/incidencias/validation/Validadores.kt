package com.condofix.incidencias.validation

import com.condofix.incidencias.model.Prioridad

object Reglas {
    const val TITULO_MIN = 5
    const val TITULO_MAX = 60
    const val UBICACION_MIN = 3
    const val DESCRIPCION_MIN = 10
    const val DESCRIPCION_MAX = 200
    const val TELEFONO_LONGITUD = 9
}

// Cada validador devuelve null si el valor es válido, o el mensaje de error.

fun validarTitulo(valor: String): String? {
    val texto = valor.trim()
    return when {
        texto.isEmpty() -> "El título es obligatorio."
        texto.length < Reglas.TITULO_MIN -> "Mínimo ${Reglas.TITULO_MIN} caracteres (actual: ${texto.length})."
        texto.length > Reglas.TITULO_MAX -> "Máximo ${Reglas.TITULO_MAX} caracteres."
        else -> null
    }
}

fun validarUbicacion(valor: String): String? {
    val texto = valor.trim()
    return when {
        texto.isEmpty() -> "La ubicación es obligatoria."
        texto.length < Reglas.UBICACION_MIN -> "Indica torre, piso o área (mín. ${Reglas.UBICACION_MIN} caracteres)."
        else -> null
    }
}

fun validarTelefono(valor: String): String? {
    val texto = valor.trim()
    return when {
        texto.isEmpty() -> "El teléfono de contacto es obligatorio."
        !texto.all { it in '0'..'9' } -> "Solo se permiten dígitos (0-9)."
        texto.length != Reglas.TELEFONO_LONGITUD -> "Debe tener exactamente ${Reglas.TELEFONO_LONGITUD} dígitos."
        !texto.startsWith("9") -> "Un celular peruano empieza con 9."
        else -> null
    }
}

fun validarDescripcion(valor: String): String? {
    val texto = valor.trim()
    return when {
        texto.isEmpty() -> "La descripción es obligatoria."
        texto.length < Reglas.DESCRIPCION_MIN -> "Describe el problema (mín. ${Reglas.DESCRIPCION_MIN} caracteres)."
        texto.length > Reglas.DESCRIPCION_MAX -> "Máximo ${Reglas.DESCRIPCION_MAX} caracteres."
        else -> null
    }
}

fun validarPrioridad(valor: Prioridad?): String? =
    if (valor == null) "Selecciona una prioridad." else null
