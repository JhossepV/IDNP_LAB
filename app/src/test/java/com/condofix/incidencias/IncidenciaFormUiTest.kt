package com.condofix.incidencias

import com.condofix.incidencias.model.Campo
import com.condofix.incidencias.model.IncidenciaFormUi
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class IncidenciaFormUiTest {

    @Test
    fun `formulario completo es valido`() {
        val ui = formularioValido()

        assertTrue(ui.esValido)
        assertTrue(ui.errores.values.all { it == null })
    }

    @Test
    fun `formulario vacio tiene un error por campo`() {
        val ui = IncidenciaFormUi()

        assertFalse(ui.esValido)
        assertEquals(Campo.entries.size, ui.errores.values.count { it != null })
    }

    @Test
    fun `el error no se muestra hasta tocar el campo`() {
        val ui = IncidenciaFormUi()

        assertNull(ui.errorVisible(Campo.TITULO))
        assertEquals("El título es obligatorio.", ui.marcarTocado(Campo.TITULO).errorVisible(Campo.TITULO))
    }

    @Test
    fun `guardar con datos invalidos no registra y muestra los errores`() {
        val ui = IncidenciaFormUi(titulo = "Fuga").guardar()

        assertTrue(ui.registradas.isEmpty())
        assertTrue(ui.intentoEnvio)
        assertNull(ui.ultimoRegistro)
        assertEquals("El teléfono de contacto es obligatorio.", ui.errorVisible(Campo.TELEFONO))
    }

    @Test
    fun `guardar con datos validos registra la incidencia y limpia el formulario`() {
        val ui = formularioValido().guardar()

        assertEquals(1, ui.registradas.size)
        assertEquals(1025, ui.registradas.first().id)
        assertEquals("", ui.titulo)
        assertNull(ui.prioridad)
        assertFalse(ui.intentoEnvio)
    }

    @Test
    fun `limpiar borra los campos pero conserva lo registrado`() {
        val ui = formularioValido().guardar()
            .conTexto(Campo.TITULO, "Otro problema")
            .limpiar()

        assertEquals("", ui.titulo)
        assertEquals(1, ui.registradas.size)
    }
}
