# CondoFix

Aplicación Android para registrar y consultar incidencias de mantenimiento en un condominio. Está desarrollada con Kotlin, Jetpack Compose, Material 3, ViewModel, StateFlow y DataStore Preferences.

## Funcionalidades implementadas

### Consulta de incidencias

- Muestra un listado inicial de **24 incidencias de prueba**, ordenadas de la más reciente a la más antigua.
- Cada incidencia incluye:
  - Identificador único.
  - Título del problema.
  - Ubicación.
  - Descripción.
  - Prioridad: alta, media o baja.
  - Estado: pendiente, en proceso o cerrada.
  - Técnico asignado o indicación de que aún no tiene asignación.
  - Tiempo relativo desde el reporte.
- Presenta tarjetas KPI con el total de incidencias:
  - 9 pendientes.
  - 6 en proceso.
  - 9 cerradas.
- Permite filtrar la lista por:
  - Todas.
  - Pendientes.
  - En proceso.
  - Cerradas.
- El filtro se guarda en **DataStore Preferences** y se recupera al volver a abrir la aplicación.
- Si el valor almacenado no existe o no es válido, se utiliza el filtro “Todos”.
- Al cambiar el filtro, la lista vuelve al inicio.
- Usa una lista eficiente (`LazyColumn`) con el identificador de cada incidencia como clave estable.

### Detalle de una incidencia

- Al tocar una tarjeta se abre la pantalla de detalle del ticket.
- El detalle muestra el número de ticket, título, prioridad, estado, ubicación, fecha relativa, técnico y descripción.
- Incluye navegación de regreso mediante el botón superior y el botón físico o gesto de retroceso del sistema.
- El detalle conserva la selección mientras se cambia el filtro.
- La posición de desplazamiento de la lista se conserva al abrir y cerrar el detalle.

### Registro de una nueva incidencia

La pantalla `NuevaIncidenciaScreen` implementa un formulario para registrar incidencias con:

- Título.
- Ubicación, con formato orientativo de torre, piso y departamento.
- Teléfono de contacto.
- Descripción.
- Prioridad alta, media o baja.
- Botón para limpiar el formulario.
- Botón para guardar, habilitado únicamente cuando todos los datos son válidos.
- Contadores de caracteres para título, teléfono y descripción.
- Mensaje de confirmación mediante `Snackbar` después de guardar.
- Lista de incidencias registradas durante la sesión actual.
- Generación de identificadores consecutivos a partir del `1025`.
- Limpieza automática del formulario después de un registro exitoso, conservando los registros creados durante la sesión.

### Validación del formulario

- Los errores se muestran después de abandonar un campo que fue utilizado o después de intentar guardar.
- El título es obligatorio y debe tener entre 5 y 60 caracteres.
- La ubicación es obligatoria y debe tener al menos 3 caracteres.
- El teléfono:
  - Es obligatorio.
  - Solo acepta dígitos.
  - Debe tener exactamente 9 dígitos.
  - Debe comenzar con `9`, de acuerdo con el formato de celular peruano.
- La descripción es obligatoria y debe tener entre 10 y 200 caracteres.
- La prioridad es obligatoria.
- Los espacios al inicio y al final se eliminan antes de validar y registrar los datos.

### Medidor visual de avance y urgencia

- Muestra cuántos de los cinco campos son válidos.
- Representa el avance mediante un anillo animado de 0 % a 100 %.
- Indica cuáles campos todavía faltan completar.
- El círculo central cambia de tamaño según la prioridad:
  - Alta: tamaño máximo.
  - Media: tamaño intermedio.
  - Baja: tamaño reducido.
  - Sin prioridad: tamaño mínimo.
- Cambia los colores y los textos de forma animada.
- Incluye descripciones semánticas para facilitar la accesibilidad.

## Arquitectura

- **UI:** Jetpack Compose y Material 3.
- **Estado:** modelos inmutables y `StateFlow`.
- **Lógica de pantalla:** `IncidenciaFormViewModel` y `ListaIncidenciasViewModel`.
- **Persistencia:** DataStore Preferences se utiliza para guardar el filtro de estado.
- **Datos actuales:** el listado principal utiliza datos de prueba en memoria; las incidencias del formulario solo se conservan durante la sesión de esa pantalla.
- **Componentes reutilizables:** tarjetas de tickets, chips de prioridad y estado, tarjetas KPI, campos de formulario, selector de prioridad y medidor de avance.

## Estructura principal

```text
app/src/main/java/com/condofix/incidencias/
├── MainActivity.kt
├── IncidenciaFormViewModel.kt
├── model/
├── validation/
├── ui/
└── lista/
    ├── ListaIncidenciasViewModel.kt
    ├── data/
    ├── model/
    └── ui/
```

## Pruebas automatizadas

El proyecto incluye pruebas unitarias para:

- Reglas de validación del formulario.
- Estado, limpieza y guardado de una incidencia.
- Cálculo del progreso y urgencia.
- Conteo y filtrado de incidencias.
- Selección y cierre del detalle.
- Conversión de filtros guardados.
- Lectura, escritura, sobrescritura y persistencia del filtro en DataStore.

## Requisitos

- Android Studio compatible con Android Gradle Plugin 9.4.1.
- JDK 11.
- Android SDK con compileSdk 37.
- Android 7.0 (API 24) o superior.

## Ejecución

Para compilar la aplicación:

```bash
./gradlew assembleDebug
```

Para ejecutar las pruebas unitarias:

```bash
./gradlew testDebugUnitTest
```

El punto de entrada actual es `MainActivity`, que muestra inicialmente `ListaIncidenciasRoute`.
