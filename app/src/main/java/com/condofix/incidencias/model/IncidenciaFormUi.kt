package com.condofix.incidencias.model

import com.condofix.incidencias.validation.validarDescripcion
import com.condofix.incidencias.validation.validarPrioridad
import com.condofix.incidencias.validation.validarTelefono
import com.condofix.incidencias.validation.validarTitulo
import com.condofix.incidencias.validation.validarUbicacion

private const val PRIMER_ID = 1025

/** Estado inmutable del formulario. Cada cambio devuelve una copia nueva. */
data class IncidenciaFormUi(
    val titulo: String = "",
    val ubicacion: String = "",
    val telefono: String = "",
    val descripcion: String = "",
    val prioridad: Prioridad? = null,
    val tocados: Set<Campo> = emptySet(),
    val intentoEnvio: Boolean = false,
    val registradas: List<Incidencia> = emptyList(),
    val ultimoRegistro: Incidencia? = null,
) {
    /** Error de cada campo (null si es válido), se esté mostrando o no. */
    val errores: Map<Campo, String?>
        get() = mapOf(
            Campo.TITULO to validarTitulo(titulo),
            Campo.UBICACION to validarUbicacion(ubicacion),
            Campo.TELEFONO to validarTelefono(telefono),
            Campo.DESCRIPCION to validarDescripcion(descripcion),
            Campo.PRIORIDAD to validarPrioridad(prioridad),
        )

    val esValido: Boolean get() = errores.values.all { it == null }

    val camposValidos: Int get() = errores.values.count { it == null }

    val totalCampos: Int get() = errores.size

    /** Avance del formulario entre 0f y 1f. */
    val progreso: Float get() = camposValidos.toFloat() / totalCampos

    val camposPendientes: List<Campo> get() = errores.filterValues { it != null }.keys.toList()

    /** El error se muestra solo si el campo ya fue tocado o si se intentó guardar. */
    fun errorVisible(campo: Campo): String? =
        if (campo in tocados || intentoEnvio) errores[campo] else null

    fun conTexto(campo: Campo, valor: String): IncidenciaFormUi {
        val actualizado = when (campo) {
            Campo.TITULO -> copy(titulo = valor)
            Campo.UBICACION -> copy(ubicacion = valor)
            Campo.TELEFONO -> copy(telefono = valor)
            Campo.DESCRIPCION -> copy(descripcion = valor)
            Campo.PRIORIDAD -> return this
        }
        return actualizado.copy(ultimoRegistro = null)
    }

    fun conPrioridad(prioridad: Prioridad): IncidenciaFormUi =
        copy(prioridad = prioridad, tocados = tocados + Campo.PRIORIDAD, ultimoRegistro = null)

    fun marcarTocado(campo: Campo): IncidenciaFormUi = copy(tocados = tocados + campo)

    /** Registra la incidencia si todo es válido; si no, activa la visualización de errores. */
    fun guardar(): IncidenciaFormUi {
        val prioridad = prioridad
        if (!esValido || prioridad == null) {
            return copy(intentoEnvio = true, ultimoRegistro = null)
        }
        val nueva = Incidencia(
            id = PRIMER_ID + registradas.size,
            titulo = titulo.trim(),
            ubicacion = ubicacion.trim(),
            telefono = telefono.trim(),
            descripcion = descripcion.trim(),
            prioridad = prioridad,
        )
        return IncidenciaFormUi(registradas = registradas + nueva, ultimoRegistro = nueva)
    }

    /** Vacía el formulario y conserva las incidencias ya registradas. */
    fun limpiar(): IncidenciaFormUi = IncidenciaFormUi(registradas = registradas)
}
