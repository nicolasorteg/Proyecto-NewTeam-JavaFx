package org.example.practicaenequipocristianvictoraitornico.players.storage

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.get
import org.example.practicaenequipocristianvictoraitornico.players.exception.PersonasException
import org.example.practicaenequipocristianvictoraitornico.players.models.*
import org.example.practicaenequipocristianvictoraitornico.players.utils.toEspecialidad
import org.example.practicaenequipocristianvictoraitornico.players.utils.toPosicion
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.io.File
import java.time.LocalDate

class PersonalStorageCsvTest {

 private val personalStorageCsv = PersonalStorageCsv()

 private val jugadorCsvLine = "1,Cristiano,Ronaldo,1985-02-05,2023-08-15,100000.0,Portugal,Jugador,,DELANTERO,7,1.87,84.0,700,900,url"
 private val entrenadorCsvLine = "2,Zinedine,Zidane,1972-06-23,2022-05-10,80000.0,Francia,Entrenador,ENTRENADOR_PRINCIPAL,,,,,,,"

 private val jugador = Jugadores(
  id = 1L,
  nombre = "Cristiano",
  apellidos = "Ronaldo",
  fechaNacimiento = LocalDate.of(1985, 2, 5),
  fechaIncorporacion = LocalDate.of(2023, 8, 15),
  salario = 100000.0,
  pais = "Portugal",
  posicion = Posicion.DELANTERO,
  dorsal = 7,
  altura = 1.87,
  peso = 84.0,
  goles = 700,
  partidosJugados = 900,
  imagen = "url"
 )
 private val entrenador = Entrenadores(
  id = 2L,
  nombre = "Zinedine",
  apellidos = "Zidane",
  fechaNacimiento = LocalDate.of(1972, 6, 23),
  fechaIncorporacion = LocalDate.of(2022, 5, 10),
  salario = 80000.0,
  pais = "Francia",
  especialidad = Especialidad.ENTRENADOR_PRINCIPAL
 )

 @Test
 @DisplayName("leerDelArchivo debería retornar Ok con una lista de personas si el archivo existe, es legible y tiene el formato correcto")
 fun leerDelArchivo_retornaOkConListaDePersonas_archivoValido() {
  val fileContent = """
            id,nombre,apellidos,fechaNacimiento,fechaIncorporacion,salario,pais,rol,extra1,extra2,extra3,extra4,extra5,extra6,extra7
            $jugadorCsvLine
            $entrenadorCsvLine
        """.trimIndent()
  val tempFile = createTempFile("testLeerDelArchivo", ".csv")
  tempFile.writeText(fileContent)

  val result = personalStorageCsv.leerDelArchivo(tempFile)

  assertTrue(result.isOk)
  assertEquals(listOf(jugador, entrenador), result.get())
  tempFile.delete()
 }

 @Test
 @DisplayName("leerDelArchivo debería retornar Err con PersonasStorageException si el archivo no existe")
 fun leerDelArchivo_retornaErrConPersonasStorageException_archivoNoExiste() {
  val nonExistentFile = File("nonExistent.csv")
  val result = personalStorageCsv.leerDelArchivo(nonExistentFile)

  assertTrue(result.isErr)
  assertTrue(result.error is PersonasException.PersonasStorageException)
  assertEquals("El fichero no existe o no se puede leer: ${nonExistentFile.path}", result.error.message)
 }

 @Test
 @DisplayName("leerDelArchivo debería retornar Err con PersonasStorageException si el archivo no es un fichero")
 fun leerDelArchivo_retornaErrConPersonasStorageException_archivoNoEsFichero() {
  val tempDir = createTempDir("testLeerDelArchivoDir")
  val result = personalStorageCsv.leerDelArchivo(tempDir)

  assertTrue(result.isErr)
  assertTrue(result.error is PersonasException.PersonasStorageException)
  assertEquals("El fichero no existe o no se puede leer: ${tempDir.path}", result.error.message)
  tempDir.deleteRecursively()
 }

 @Test
 @DisplayName("leerDelArchivo debería retornar Err con PersonasStorageException si el archivo no se puede leer")
 fun leerDelArchivo_retornaErrConPersonasStorageException_archivoNoSePuedeLeer() {
  val tempFile = createTempFile("testLeerDelArchivoNoLeer", ".csv")
  tempFile.setReadable(false)
  val result = personalStorageCsv.leerDelArchivo(tempFile)

  assertTrue(result.isErr)
  assertTrue(result.error is PersonasException.PersonasStorageException)
  assertEquals("El fichero no existe o no se puede leer: ${tempFile.path}", result.error.message)
  tempFile.delete()
 }

 @Test
 @DisplayName("leerDelArchivo debería retornar Err con PersonasInvalidoException si el rol es desconocido")
 fun leerDelArchivo_retornaErrConPersonasInvalidoException_rolDesconocido() {
  val fileContent = """
            id,nombre,apellidos,fechaNacimiento,fechaIncorporacion,salario,pais,rol,extra1,extra2,extra3,extra4,extra5,extra6,extra7
            1,Test,User,2000-01-01,2024-01-01,50000.0,Spain,Desconocido,,,,,,,
        """.trimIndent()
  val tempFile = createTempFile("testLeerDelArchivoRolDesconocido", ".csv")
  tempFile.writeText(fileContent)

  val result = personalStorageCsv.leerDelArchivo(tempFile)

  assertTrue(result.isErr)
  assertTrue(result.error is PersonasException.PersonasInvalidoException)
  assertEquals("tiene que ser o un jugador o un entrenador", result.error.message)
  tempFile.delete()
 }

 @Test
 @DisplayName("escribirAUnArchivo debería retornar Ok con la ruta del archivo si la escritura es exitosa")
 fun escribirAUnArchivo_retornaOkConRutaArchivo_escrituraExitosa() {
  val tempFile = createTempFile("testEscribirArchivo", ".csv")
  val personas = listOf(jugador, entrenador)

  val result = personalStorageCsv.escribirAUnArchivo(tempFile, personas)

  assertTrue(result.isOk)
  assertEquals(tempFile.absolutePath, result.get())

  val fileContent = tempFile.readText()
  assertTrue(fileContent.contains(jugadorCsvLine))
  assertTrue(fileContent.contains(entrenadorCsvLine.replace(",,,,,,,", ",ENTRENADOR_PRINCIPAL,,,,,,,,")))
  tempFile.delete()
 }

 @Test
 @DisplayName("escribirAUnArchivo debería retornar Err con PersonasStorageException si el directorio padre no existe")
 fun escribirAUnArchivo_retornaErrConPersonasStorageException_directorioPadreNoExiste() {
  val nonExistentDir = File("nonExistentDir")
  val tempFile = File(nonExistentDir, "test.csv")

  val result = personalStorageCsv.escribirAUnArchivo(tempFile, listOf(jugador))

  assertTrue(result.isErr)
  assertTrue(result.error is PersonasException.PersonasStorageException)
  assertEquals("No se puede escribir en el archivo debido a que no existe o no es de la extensión adecuada 😔", result.error.message)
 }

 @Test
 @DisplayName("escribirAUnArchivo debería retornar Err con PersonasStorageException si el archivo no tiene extensión .csv")
 fun escribirAUnArchivo_retornaErrConPersonasStorageException_extensionArchivoInvalida() {
  val tempFile = createTempFile("testEscribirArchivo", ".txt")
  val result = personalStorageCsv.escribirAUnArchivo(tempFile, listOf(jugador))

  assertTrue(result.isErr)
  assertTrue(result.error is PersonasException.PersonasStorageException)
  assertEquals("No se puede escribir en el archivo debido a que no existe o no es de la extensión adecuada 😔", result.error.message)
  tempFile.delete()
 }

 @Test
 @DisplayName("escribirAUnArchivo debería retornar Err con PersonasStorageException si la persona es de un tipo desconocido")
 fun escribirAUnArchivo_retornaErrConPersonasStorageException_tipoPersonaDesconocido() {
  val personaDesconocida = object : Persona {}
  val tempFile = createTempFile("testEscribirArchivo", ".csv")

  val result = personalStorageCsv.escribirAUnArchivo(tempFile, listOf(personaDesconocida))

  assertTrue(result.isErr)
  assertTrue(result.error is PersonasException.PersonasStorageException)
  assertEquals("Tipo de persona desconocido: Persona\$1", result.error.message)
  tempFile.delete()
 }

 private fun createTempDir(prefix: String): File {
  val tempDir = File.createTempFile(prefix, "", File("."))
  tempDir.delete()
  tempDir.mkdirs()
  return tempDir
 }
}