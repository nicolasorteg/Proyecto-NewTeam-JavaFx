package org.example.practicaenequipocristianvictoraitornico.players.storage

import org.example.practicaenequipocristianvictoraitornico.players.models.*
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.lighthousegames.logging.logging
import java.io.File
import java.nio.file.Files
import java.time.LocalDate

class PersonalStorageXmlTest {

 private lateinit var storageXml: PersonalStorageXml
 private lateinit var myFile: File
 private val logger = logging()

 @BeforeEach
 fun setUp() {
  storageXml = PersonalStorageXml()
  myFile = Files.createTempFile("plantilla", ".xml").toFile()
 }

 @AfterEach
 fun tearDown() {
  Files.deleteIfExists(myFile.toPath())
 }

 @Test
 @DisplayName("Test de correcta lectura en XML.")
 fun leerDelArchivo() {
  logger.debug { "Testeando lectura en XML..." }

  val xmlContent = """
            <EquipoDtoXml>
              <personal>
                <PersonalDtoXml>
                  <id>1</id>
                  <tipo>Jugador</tipo>
                  <nombre>Cristian</nombre>
                  <apellidos>Ortega</apellidos>
                  <fechaNacimiento>2003-05-05</fechaNacimiento>
                  <fechaIncorporacion>2025-05-05</fechaIncorporacion>
                  <salario>1200.0</salario>
                  <pais>España</pais>
                  <posicion>CENTROCAMPISTA</posicion>
                  <dorsal>8</dorsal>
                  <altura>1.75</altura>
                  <peso>60.0</peso>
                  <goles>35</goles>
                  <partidosJugados>30</partidosJugados>
                  <imagen>https://www.hdwallpapers.in/download/cristiano_ronaldo_cr7_with_cup_in_blur_stadium_background_is_wearing_white_sports_dress_hd_cristiano_ronaldo-HD.jpg</imagen>
                </PersonalDtoXml>
                <PersonalDtoXml>
                  <id>2</id>
                  <tipo>Entrenador</tipo>
                  <nombre>Cristina</nombre>
                  <apellidos>Ortega</apellidos>
                  <fechaNacimiento>2003-05-05</fechaNacimiento>
                  <fechaIncorporacion>2025-05-05</fechaIncorporacion>
                  <salario>5000.0</salario>
                  <pais>España</pais>
                  <especialidad>ENTRENADOR_PORTEROS</especialidad>
                </PersonalDtoXml>
              </personal>
            </EquipoDtoXml>
        """.trimIndent()

  myFile.writeText(xmlContent)

  val result = storageXml.leerDelArchivo(myFile)

  assertAll(
   { assertTrue(result.isOk, "Debe ser Ok") },
   { assertEquals(2, result.value.size, "Debe contener dos personas") },
   { assertTrue(result.value.any { it.nombre == "Cristian" }, "Debe contener a Cristian") },
   { assertTrue(result.value.any { it is Entrenadores && it.especialidad == Especialidad.ENTRENADOR_PORTEROS }, "Debe tener entrenador con especialidad correcta") }
  )
 }

 @Test
 @DisplayName("Test de correcta escritura en XML.")
 fun escribirAUnArchivo() {
  logger.debug { "Testeando escritura en XML..." }

  val personas = listOf(
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
    fechaIncorporacion = LocalDate.of(2025, 5, 5),
    salario = 5000.0,
    pais = "España",
    especialidad = Especialidad.ENTRENADOR_PORTEROS
   )
  )

  val result = storageXml.escribirAUnArchivo(myFile, personas)

  assertAll(
   { assertTrue(result.isOk, "Debe ser Ok") },
   { assertTrue(myFile.exists(), "El archivo debe existir") },
   { assertTrue(myFile.readText().isNotBlank(), "El archivo no debe estar vacío") },
   { assertTrue(myFile.readText().contains("Cristian"), "El archivo debe contener el nombre del jugador") },
   { assertTrue(myFile.readText().contains("ENTRENADOR_PORTEROS"), "Debe contener la especialidad del entrenador") }
  )
 }
}
