package com.condofix.incidencias.lista

import com.condofix.incidencias.lista.model.Estado
import com.condofix.incidencias.lista.model.FiltroEstado
import com.condofix.incidencias.lista.model.ListaIncidenciasUi
import com.condofix.incidencias.lista.model.Prioridad
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ListaIncidenciasUiTest {

    private val ui = ListaIncidenciasUi()

    @Test fun l01_laColeccionTieneAlMenosVeinteRegistros() {
        assertTrue(ui.incidencias.size >= 20)
    }

    @Test fun l02_losIdSonUnicosParaUsarlosComoClaveEstable() {
        assertEquals(ui.incidencias.size, ui.incidencias.map { it.id }.toSet().size)
    }

    @Test fun l03_cadaRegistroTieneSusAtributosVisiblesCompletos() {
        assertTrue(ui.incidencias.all {
            it.titulo.isNotBlank() && it.ubicacion.isNotBlank() && it.descripcion.isNotBlank() && it.reportado.isNotBlank()
        })
    }

    @Test fun l04_losContadoresDeEstadoSumanElTotal() {
        val suma = Estado.entries.sumOf { ui.contar(it) }
        assertEquals(ui.incidencias.size, suma)
        assertEquals(9, ui.contar(Estado.PENDIENTE))
        assertEquals(6, ui.contar(Estado.EN_PROCESO))
        assertEquals(9, ui.contar(Estado.CERRADA))
    }

    @Test fun l05_laIncidencia1024CoincideConElPrototipoDeFigma() {
        val t = ui.incidencias.first { it.id == 1024 }
        assertEquals("Fuga de agua en baño", t.titulo)
        assertEquals(Prioridad.ALTA, t.prioridad)
        assertEquals(Estado.PENDIENTE, t.estado)
    }

    @Test fun f01_filtroTodosMuestraLos24() {
        assertEquals(24, ui.conFiltro(FiltroEstado.TODOS).visibles.size)
    }

    @Test fun f02_filtroPendientesSoloMuestraPendientes() {
        val v = ui.conFiltro(FiltroEstado.PENDIENTES).visibles
        assertEquals(9, v.size)
        assertTrue(v.all { it.estado == Estado.PENDIENTE })
    }

    @Test fun f03_filtroCerradasMuestraLas9Cerradas() {
        val v = ui.conFiltro(FiltroEstado.CERRADAS).visibles
        assertEquals(9, v.size)
        assertTrue(v.all { it.estado == Estado.CERRADA })
    }

    @Test fun f04_filtrarConservaElOrdenDeMasRecienteAMasAntigua() {
        val ids = ui.conFiltro(FiltroEstado.EN_PROCESO).visibles.map { it.id }
        assertEquals(ids.sortedDescending(), ids)
    }

    @Test fun s01_seleccionarAbreLaIncidenciaCorrecta() {
        assertEquals("Fuga de agua en baño", ui.seleccionar(1024).seleccionada?.titulo)
    }

    @Test fun s02_seleccionarUnIdInexistenteNoAbreNada() {
        assertNull(ui.seleccionar(9999).seleccionada)
    }

    @Test fun s03_cerrarDetalleVuelveALaLista() {
        assertNull(ui.seleccionar(1024).cerrarDetalle().seleccionada)
    }

    @Test fun s04_cambiarElFiltroNoCierraElDetalleAbierto() {
        assertEquals(1015, ui.seleccionar(1015).conFiltro(FiltroEstado.CERRADAS).seleccionada?.id)
    }
}
