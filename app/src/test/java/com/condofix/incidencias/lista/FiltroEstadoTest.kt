package com.condofix.incidencias.lista

import com.condofix.incidencias.lista.model.FiltroEstado
import org.junit.Assert.assertEquals
import org.junit.Test

class FiltroEstadoTest {
    @Test fun n01_nulo_usaTodos() = assertEquals(FiltroEstado.TODOS, FiltroEstado.desdeNombre(null))

    @Test fun n02_idaYVuelta_conservaCadaValor() {
        FiltroEstado.entries.forEach { assertEquals(it, FiltroEstado.desdeNombre(it.name)) }
    }

    @Test fun n03_textoDesconocido_usaTodos() = assertEquals(FiltroEstado.TODOS, FiltroEstado.desdeNombre("XYZ"))
}
