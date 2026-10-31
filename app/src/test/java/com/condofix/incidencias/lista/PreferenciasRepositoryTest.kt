package com.condofix.incidencias.lista

import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.condofix.incidencias.lista.data.PreferenciasRepository
import com.condofix.incidencias.lista.model.FiltroEstado
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import java.io.File

class PreferenciasRepositoryTest {
    @get:Rule val carpeta = TemporaryFolder()
    private val archivo get() = File(carpeta.root, "ajustes_test.preferences_pb")

    private fun crear(job: Job) = PreferenceDataStoreFactory.create(
        scope = CoroutineScope(job + Dispatchers.IO),
        produceFile = { archivo },
    )

    @Test fun d01_valorInicial_esTodos() = runTest {
        val job = Job()
        assertEquals(FiltroEstado.TODOS, PreferenciasRepository(crear(job)).filtro.first())
        job.cancelAndJoin()
    }

    @Test fun d02_guardarYLeer() = runTest {
        val job = Job()
        val repo = PreferenciasRepository(crear(job))
        repo.guardarFiltro(FiltroEstado.PENDIENTES)
        assertEquals(FiltroEstado.PENDIENTES, repo.filtro.first())
        job.cancelAndJoin()
    }

    @Test fun d03_valorSobreviveAlReinicio() = runTest {
        val job1 = Job()
        PreferenciasRepository(crear(job1)).guardarFiltro(FiltroEstado.CERRADAS)
        job1.cancelAndJoin() // "cierra la app": libera el archivo

        val job2 = Job() // "reinicio": instancia nueva sobre el mismo archivo
        assertEquals(FiltroEstado.CERRADAS, PreferenciasRepository(crear(job2)).filtro.first())
        job2.cancelAndJoin()
    }

    @Test fun d04_valorInvalido_usaTodos() = runTest {
        val job = Job()
        val ds = crear(job)
        ds.edit { it[stringPreferencesKey("filtro_estado")] = "XYZ" }
        assertEquals(FiltroEstado.TODOS, PreferenciasRepository(ds).filtro.first())
        job.cancelAndJoin()
    }

    @Test fun d05_sobrescritura() = runTest {
        val job = Job()
        val repo = PreferenciasRepository(crear(job))
        repo.guardarFiltro(FiltroEstado.CERRADAS)
        repo.guardarFiltro(FiltroEstado.EN_PROCESO)
        assertEquals(FiltroEstado.EN_PROCESO, repo.filtro.first())
        job.cancelAndJoin()
    }
}
