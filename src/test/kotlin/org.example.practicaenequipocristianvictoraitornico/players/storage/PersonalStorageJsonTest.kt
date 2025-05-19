package org.example.practicaenequipocristianvictoraitornico.players.storage

import org.example.practicaenequipocristianvictoraitornico.players.models.Entrenadores
import org.example.practicaenequipocristianvictoraitornico.players.models.Especialidad
import org.example.practicaenequipocristianvictoraitornico.players.models.Jugadores
import org.example.practicaenequipocristianvictoraitornico.players.models.Posicion
import org.junit.jupiter.api.*

import org.junit.jupiter.api.Assertions.*
import org.lighthousegames.logging.logging
import java.io.File
import java.nio.file.Files
import java.time.LocalDate

/**
 * Esta clase se encarga de testear la lectura y escritura de archivos JSON
 */
class PersonalStorageJsonTest {

  private lateinit var storageJson: PersonalStorageJson
  private lateinit var myFile: File
  private val logger = logging()

  @BeforeEach
  fun setUp() {
   storageJson = PersonalStorageJson()
   myFile = Files.createTempFile("plantilla", ".json").toFile()
  }

  @AfterEach
  fun tearDown() {
   Files.deleteIfExists(myFile.toPath())
  }

  @Test
  @DisplayName("Leer datos de JSON.")
   fun leerDelArchivo() {

   logger.debug { "Testeando la lectura de datos en JSON..." }

   val json = """
    [
      {
        "tipo": "Jugador",
        "id": "1",
        "nombre": "Cristian",
        "apellidos": "Ortega",
        "fechaNacimiento": "2003-05-05",
        "fechaIncorporacion": "2025-05-05",
        "salario": "1200.0",
        "pais": "España",
        "posicion": "CENTROCAMPISTA",
        "dorsal": "8",
        "altura": "1.70",
        "peso": "60.0",
        "goles": "35",
        "partidosJugados": "30",
        "imagen": "https://www.hdwallpapers.in/download/cristiano_ronaldo_cr7_with_cup_in_blur_stadium_background_is_wearing_white_sports_dress_hd_cristiano_ronaldo-HD.jpg"
      },
      {
        "tipo": "Entrenador",
        "id": "2",
        "nombre": "Cristina",
        "apellidos": "Ortega",
        "fechaNacimiento": "2003-05-05",
        "fechaIncorporacion": "2025-05-05",
        "salario": "5000.0",
        "pais": "España",
        "especialidad": "ENTRENADOR_PORTEROS"
      }
    ]
   """.trimIndent()

    myFile.writeText(json)

    val result = storageJson.leerDelArchivo(myFile)

   assertAll(
    { assertTrue(result.isOk, "El resultado debe ser Ok") },
    { assertTrue(myFile.exists(), "El archivo debe existir tras escribir los datos") },
    { assertTrue(myFile.readText().contains("Cristian"), "El archivo debe contener el nombre del jugador") },
    { assertTrue(myFile.readText().contains("ENTRENADOR_PORTEROS"), "El archivo debe contener la especialidad del entrenador") }
   )
  }

  @Test
  @DisplayName("Escribir datos de un JSON")
  fun escribirAUnArchivo() {

   logger.debug { "Testeando la escritura de datos en JSON..." }

   val data = listOf(
    Jugadores(
     id = 1,
     nombre = "Cristian",
     apellidos = "Ortega",
     fechaNacimiento = LocalDate.of(2003, 5, 5),
     fechaIncorporacion = LocalDate.of(2025, 5, 5),
     salario = 1200.0,
     pais = "España",
     posicion = Posicion.CENTROCAMPISTA,
     dorsal = 8,
     altura = 1.75,
     peso = 60.0,
     goles = 35,
     partidosJugados = 30,
     imagen = "https://www.hdwallpapers.in/download/cristiano_ronaldo_cr7_with_cup_in_blur_stadium_background_is_wearing_white_sports_dress_hd_cristiano_ronaldo-HD.jpg"
    ),
    Entrenadores(
     id = 2,
     nombre = "Cristina",
     apellidos = "Ortega",
     fechaNacimiento = LocalDate.of(2003, 5, 5),
     fechaIncorporacion = LocalDate.of(2003, 5, 5),
     salario = 800.0,
     pais = "España",
     especialidad = Especialidad.ENTRENADOR_PORTEROS
    )
   )

   val result = storageJson.escribirAUnArchivo(myFile, data)

   assertAll(
    { assertTrue(result.isOk, "El resultado debe ser Ok") },
    { assertTrue(result.value.isNotEmpty(), "El mensaje de resultado no debe estar vacío") },
    { assertTrue(myFile.exists(), "El archivo debe existir tras almacenar los datos") },
    { assertTrue(myFile.readText().isNotEmpty(), "El archivo no debe estar vacío") },
    { assertTrue(myFile.readText().contains("Cristian"), "El archivo debe contener el nombre del jugador") },
    { assertTrue(myFile.readText().contains("Ortega"), "El archivo debería contener el apellido del jugador") }
   )
  }
}