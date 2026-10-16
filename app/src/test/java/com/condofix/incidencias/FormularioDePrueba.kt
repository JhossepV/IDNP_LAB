package com.condofix.incidencias

import com.condofix.incidencias.model.IncidenciaFormUi
import com.condofix.incidencias.model.Prioridad

internal fun formularioValido() = IncidenciaFormUi(
    titulo = "Fuga de agua en baño",
    ubicacion = "Torre A · Piso 3 · Apto 302",
    telefono = "987654321",
    descripcion = "El baño del dormitorio principal tiene una fuga bajo el lavabo.",
    prioridad = Prioridad.ALTA,
)
