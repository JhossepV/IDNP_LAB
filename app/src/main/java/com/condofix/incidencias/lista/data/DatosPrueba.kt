package com.condofix.incidencias.lista.data

import com.condofix.incidencias.lista.model.Estado.CERRADA
import com.condofix.incidencias.lista.model.Estado.EN_PROCESO
import com.condofix.incidencias.lista.model.Estado.PENDIENTE
import com.condofix.incidencias.lista.model.Incidencia
import com.condofix.incidencias.lista.model.Prioridad.ALTA
import com.condofix.incidencias.lista.model.Prioridad.BAJA
import com.condofix.incidencias.lista.model.Prioridad.MEDIA

/** 24 incidencias de prueba, de la más reciente (#1024, la del prototipo de Figma) a la más antigua. */
object DatosPrueba {
    fun incidencias(): List<Incidencia> = listOf(
        Incidencia(1024, "Fuga de agua en baño", "Torre A · Piso 3 · Apto 302", "El baño del dormitorio principal tiene una fuga bajo el lavabo.", ALTA, PENDIENTE, null, "Hace 2 h"),
        Incidencia(1023, "Cambio de luminaria en pasillo", "Torre B · Piso 5 · Área común", "La luminaria del pasillo parpadea y debe reemplazarse.", MEDIA, EN_PROCESO, "Luis R.", "Hace 4 h"),
        Incidencia(1022, "Puerta principal no cierra", "Lobby", "La puerta del lobby no cierra por completo y deja entrar aire.", MEDIA, EN_PROCESO, "Carlos R.", "Hace 6 h"),
        Incidencia(1021, "Ascensor detenido entre pisos", "Torre A · Ascensor 2", "El ascensor se detuvo entre los pisos 4 y 5; requiere revisión urgente.", ALTA, PENDIENTE, null, "Ayer"),
        Incidencia(1020, "Corto circuito en tablero", "Torre B · Sótano", "Se activó el térmico general del sótano dos veces hoy.", ALTA, EN_PROCESO, "Miguel A.", "Ayer"),
        Incidencia(1019, "Goteo en techo del estacionamiento", "Estacionamiento · Nivel 1", "Hay goteo constante cerca de la rampa de entrada.", MEDIA, PENDIENTE, null, "Ayer"),
        Incidencia(1018, "Intercomunicador sin audio", "Torre A · Piso 2 · Apto 204", "El intercomunicador muestra video pero no transmite audio.", BAJA, CERRADA, "Miguel A.", "Hace 2 días"),
        Incidencia(1017, "Bombillas quemadas en sótano", "Torre B · Sótano", "Dos bombillas del pasillo de trasteros están quemadas.", BAJA, CERRADA, "Luis R.", "Hace 2 días"),
        Incidencia(1016, "Portón eléctrico lento", "Estacionamiento · Ingreso", "El portón tarda más de un minuto en abrir.", MEDIA, PENDIENTE, null, "Hace 3 días"),
        Incidencia(1015, "Filtración en pared de sala", "Torre A · Piso 6 · Apto 601", "Mancha de humedad creciente en la pared compartida con el baño.", ALTA, EN_PROCESO, "Carlos R.", "Hace 3 días"),
        Incidencia(1014, "Pintura descascarada en fachada", "Torre B · Fachada norte", "Se desprende pintura en el tramo del piso 7.", BAJA, PENDIENTE, null, "Hace 4 días"),
        Incidencia(1013, "Cámara de seguridad sin señal", "Lobby", "La cámara 3 del lobby dejó de transmitir al monitor de portería.", ALTA, CERRADA, "Miguel A.", "Hace 4 días"),
        Incidencia(1012, "Bomba de agua con ruido", "Cuarto de máquinas", "La bomba principal emite un ruido metálico al arrancar.", MEDIA, EN_PROCESO, "Luis R.", "Hace 5 días"),
        Incidencia(1011, "Cerradura dañada en sala comunal", "Sala comunal", "La llave no gira bien y la puerta se atasca.", MEDIA, CERRADA, "Carlos R.", "Hace 5 días"),
        Incidencia(1010, "Aspersor de riego averiado", "Áreas verdes · Entrada", "Un aspersor está roto y moja la vereda.", BAJA, PENDIENTE, null, "Hace 6 días"),
        Incidencia(1009, "Olor a gas en cocina", "Torre B · Piso 1 · Apto 105", "La residente reporta olor leve a gas cerca de la cocina.", ALTA, CERRADA, "Miguel A.", "Hace 6 días"),
        Incidencia(1008, "Ventana que no sella", "Torre A · Piso 4 · Apto 402", "La ventana de la sala deja pasar ruido y aire.", BAJA, PENDIENTE, null, "Hace 1 semana"),
        Incidencia(1007, "Basurero comunal desbordado", "Patio de servicio", "El recolector no se vació y atrae insectos.", MEDIA, CERRADA, "Luis R.", "Hace 1 semana"),
        Incidencia(1006, "Timbre sin sonido", "Torre B · Piso 3 · Apto 303", "El timbre no suena al ser pulsado desde el pasillo.", BAJA, CERRADA, "Carlos R.", "Hace 1 semana"),
        Incidencia(1005, "Piso resbaladizo en lobby", "Lobby", "El piso queda resbaladizo después de la limpieza.", MEDIA, PENDIENTE, null, "Hace 8 días"),
        Incidencia(1004, "Sensor de luz del pasillo fallando", "Torre A · Piso 7 · Área común", "El sensor no enciende la luz al detectar movimiento.", BAJA, EN_PROCESO, "Miguel A.", "Hace 9 días"),
        Incidencia(1003, "Tubería con óxido en azotea", "Torre B · Azotea", "Se observa óxido y pequeñas fugas en la tubería principal.", ALTA, PENDIENTE, null, "Hace 10 días"),
        Incidencia(1002, "Reja peatonal descuadrada", "Ingreso peatonal", "La reja roza el suelo y se abre con dificultad.", MEDIA, CERRADA, "Luis R.", "Hace 11 días"),
        Incidencia(1001, "Extintor vencido en piso 5", "Torre A · Piso 5 · Área común", "La etiqueta del extintor indica vencimiento del mes pasado.", MEDIA, CERRADA, "Carlos R.", "Hace 12 días"),
    )
}
