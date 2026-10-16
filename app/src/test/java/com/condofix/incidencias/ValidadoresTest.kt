package com.condofix.incidencias

import com.condofix.incidencias.validation.validarDescripcion
import com.condofix.incidencias.validation.validarPrioridad
import com.condofix.incidencias.validation.validarTelefono
import com.condofix.incidencias.validation.validarTitulo
import org.junit.Assert.assertEquals
import org.junit.Test

class ValidadoresTest {

    @Test
    fun `titulo corto indica cuantos caracteres tiene`() {
        assertEquals("Mínimo 5 caracteres (actual: 4).", validarTitulo("Fuga"))
    }

    @Test
    fun `titulo con solo espacios se trata como vacio`() {
        assertEquals("El título es obligatorio.", validarTitulo("      "))
    }

    @Test
    fun `titulo de mas de 60 caracteres es rechazado`() {
        assertEquals("Máximo 60 caracteres.", validarTitulo("a".repeat(61)))
    }

    @Test
    fun `telefono con letras es rechazado`() {
        assertEquals("Solo se permiten dígitos (0-9).", validarTelefono("98765A321"))
    }

    @Test
    fun `telefono de 8 digitos es rechazado`() {
        assertEquals("Debe tener exactamente 9 dígitos.", validarTelefono("98765432"))
    }

    @Test
    fun `telefono que no empieza con 9 es rechazado`() {
        assertEquals("Un celular peruano empieza con 9.", validarTelefono("887654321"))
    }

    @Test
    fun `descripcion corta es rechazada`() {
        assertEquals("Describe el problema (mín. 10 caracteres).", validarDescripcion("Fuga"))
    }

    @Test
    fun `prioridad sin elegir es rechazada`() {
        assertEquals("Selecciona una prioridad.", validarPrioridad(null))
    }
}
