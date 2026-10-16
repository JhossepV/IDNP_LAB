package com.condofix.incidencias

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.condofix.incidencias.ui.NuevaIncidenciaRoute
import com.condofix.incidencias.ui.theme.CondoFixTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CondoFixTheme {
                NuevaIncidenciaRoute()
            }
        }
    }
}
