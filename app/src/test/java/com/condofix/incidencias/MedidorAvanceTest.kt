package com.condofix.incidencias

import com.condofix.incidencias.model.Campo
import com.condofix.incidencias.model.IncidenciaFormUi
import com.condofix.incidencias.model.Prioridad
import com.condofix.incidencias.model.escalaUrgencia
import com.condofix.incidencias.model.textoAvance
import com.condofix.incidencias.model.textoUrgencia
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class MedidorAvanceTest {

    private val delta = 0.0001f

    @Test
    fun `formulario vacio no tiene progreso`() {
        val ui = IncidenciaFormUi()

        assertEquals(0, ui.camposValidos)
        assertEquals(0f, ui.progreso, delta)
        assertEquals(Campo.entries.size, ui.camposPendientes.size)
    }

    @Test
    fun `un campo valido avanza un quinto`() {
        val ui = IncidenciaFormUi().conTexto(Campo.TITULO, "Fuga de agua en baño")

        assertEquals(1, ui.camposValidos)
        assertEquals(0.2f, ui.progreso, delta)
    }

    @Test
    fun `formulario completo llega al cien por ciento`() {
        val ui = formularioValido()

        assertEquals(5, ui.camposValidos)
        assertEquals(1f, ui.progreso, delta)
        assertTrue(ui.camposPendientes.isEmpty())
    }

    @Test
    fun `elegir prioridad cuenta como campo valido`() {
        val ui = IncidenciaFormUi().conPrioridad(Prioridad.MEDIA)

        assertEquals(1, ui.camposValidos)
        assertEquals(Prioridad.MEDIA, ui.prioridad)
    }

    @Test
    fun `la escala del circulo crece con la urgencia`() {
        assertTrue(escalaUrgencia(Prioridad.ALTA) > escalaUrgencia(Prioridad.MEDIA))
        assertTrue(escalaUrgencia(Prioridad.MEDIA) > escalaUrgencia(Prioridad.BAJA))
        assertTrue(escalaUrgencia(Prioridad.BAJA) > escalaUrgencia(null))
        assertEquals(1f, escalaUrgencia(Prioridad.ALTA), delta)
    }

    @Test
    fun `textoAvance lista los campos pendientes`() {
        val ui = formularioValido().conTexto(Campo.TELEFONO, "123")

        assertEquals("Faltan: Teléfono.", textoAvance(ui))
    }

    @Test
    fun `textoAvance indica cuando el reporte esta completo`() {
        assertEquals("Reporte completo: listo para guardar.", textoAvance(formularioValido()))
    }

    @Test
    fun `guardar reinicia el progreso`() {
        val ui = formularioValido().guardar()

        assertEquals(0f, ui.progreso, delta)
        assertEquals(escalaUrgencia(null), escalaUrgencia(ui.prioridad), delta)
    }

    @Test
    fun `limpiar vuelve el medidor al estado inicial`() {
        val ui = formularioValido().limpiar()

        assertEquals(0f, ui.progreso, delta)
        assertEquals("Elige una prioridad para ver la urgencia.", textoUrgencia(ui.prioridad))
    }
}
