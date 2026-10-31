# CondoFix – Lab 08 (DataStore: filtro persistente)

Archivos Kotlin del paquete `com.condofix.incidencias`. Cópialos a un proyecto Compose (Empty Activity):

- `app/src/main/java/...`  → agrega/reemplaza en tu módulo `app` (reemplaza `MainActivity.kt` y `ui/theme/Theme.kt`)
- `app/src/test/java/...`  → pruebas unitarias (JUnit 4)

Dependencia extra en `app/build.gradle.kts` (la misma del Lab 05):

    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.7")

Si algún import de iconos falla (`Icons.AutoMirrored...`), usa `Icons.Filled.ArrowBack` o actualiza el BOM de Compose.
Ejecutar pruebas: `./gradlew testDebugUnitTest`

## Lab 08
Nuevo: `lista/data/PreferenciasRepository.kt` (DataStore Preferences, clave `filtro_estado`), `FiltroEstado.desdeNombre`,
ViewModel con fábrica y pruebas `FiltroEstadoTest` / `PreferenciasRepositoryTest`.
Dependencias en `gradle/libs.versions.toml`: `datastore-preferences 1.3.0`, `kotlinx-coroutines-test 1.10.2`.
