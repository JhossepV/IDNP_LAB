package com.condofix.incidencias.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val Colores = lightColorScheme(
    primary = AzulCondoFix,
    onPrimary = Color.White,
)

@Composable
fun CondoFixTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = Colores, content = content)
}