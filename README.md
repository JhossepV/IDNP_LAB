# CondoFix – Lab 07 (LazyColumn)

Archivos Kotlin del paquete `com.condofix.incidencias`. Cópialos a un proyecto Compose (Empty Activity):

- `app/src/main/java/...`  → agrega/reemplaza en tu módulo `app` (reemplaza `MainActivity.kt` y `ui/theme/Theme.kt`)
- `app/src/test/java/...`  → pruebas unitarias (JUnit 4)

Dependencia extra en `app/build.gradle.kts` (la misma del Lab 05):

    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.7")

Si algún import de iconos falla (`Icons.AutoMirrored...`), usa `Icons.Filled.ArrowBack` o actualiza el BOM de Compose.
Ejecutar pruebas: `./gradlew testDebugUnitTest`
