package com.condofix.incidencias.lista.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.condofix.incidencias.lista.model.FiltroEstado
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import java.io.IOException

/** Una sola instancia por proceso: se declara a nivel de archivo. */
val Context.ajustesDataStore: DataStore<Preferences> by preferencesDataStore(name = "ajustes_condofix")

class PreferenciasRepository(private val dataStore: DataStore<Preferences>) {

    private object Claves {
        val FILTRO_ESTADO = stringPreferencesKey("filtro_estado")
    }

    /** Emite el filtro guardado y vuelve a emitir cada vez que cambia. */
    val filtro: Flow<FiltroEstado> = dataStore.data
        .catch { e -> if (e is IOException) emit(emptyPreferences()) else throw e }
        .map { prefs -> FiltroEstado.desdeNombre(prefs[Claves.FILTRO_ESTADO]) }
        .distinctUntilChanged()

    /** Suspende (no bloquea el hilo principal) hasta que el valor queda escrito. */
    suspend fun guardarFiltro(filtro: FiltroEstado) {
        dataStore.edit { prefs -> prefs[Claves.FILTRO_ESTADO] = filtro.name }
    }
}
