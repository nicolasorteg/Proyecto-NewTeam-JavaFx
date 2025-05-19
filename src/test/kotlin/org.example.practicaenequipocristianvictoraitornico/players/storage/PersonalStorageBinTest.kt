package org.example.practicaenequipocristianvictoraitornico.players.storage

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.get
import org.example.practicaenequipocristianvictoraitornico.players.dto.EntrenadorDto
import org.example.practicaenequipocristianvictoraitornico.players.dto.JugadorDto
import org.example.practicaenequipocristianvictoraitornico.players.exception.PersonasException
import org.example.practicaenequipocristianvictoraitornico.players.mappers.PersonaMapper
import org.example.practicaenequipocristianvictoraitornico.players.models.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.lighthousegames.logging.logging
import java.io.File
import java.io.RandomAccessFile
import java.time.LocalDate
/*
class PersonalStorageBinTest {
 private val logger = logging()
 private val personaMapper = PersonaMapper()
 private val personalStorageBin = PersonalStorageBin()

 private val jugador = Jugadores(
  id = 1L,
  nombre = "Cristiano",
  apellidos = "Ronaldo",
  salario = 100000.0,
  pais = "Portugal",
  fechaNacimiento = LocalDate.of(1985, 2, 5),
  fechaIncorporacion = LocalDate.now(),
  imagen = "url",
  posicion = Posicion.DELANTERO,
  dorsal = 7,
  altura = 1.87,
  peso = 84.0,
  goles = 700,
  partidosJugados = 900
 )
 private val entrenador = Entrenadores(
  id = 2L,
  nombre = "Zinedine",
  apellidos = "Zidane",
  salario = 80000.0,
  pais = "Francia",
  fechaNacimiento = LocalDate.of(1972, 6, 23),
  fechaIncorporacion = LocalDate.now(),
  especialidad = Especialidad.ENTRENADOR_PRINCIPAL
 )

 @Test
 @DisplayName("leerDelArchivo debería retornar Ok con una lista de personas si el archivo existe, es legible y tiene contenido")
 fun leerDelArchivo_ReturnOkWithListOfPersonas() {
  logger.debug { "Test: leerDelArchivo debería retornar Ok con una lista de personas..." }
  val fileContent = """
            Jugador
            1
            Cristiano
            Ronaldo
            1985-02-05
            ${LocalDate.now()}
            100000.0
            Portugal
            DELANTERO
            7
            1.87
            84.0
            700
            900
            url
            Entrenador
            2
            Zinedine
            Zidane
            1972-06-23
            ${LocalDate.now()}
            80000.0
            Francia
            ATAQUE
        """.trimIndent()
  val tempFile = createTempFile("testLeerDelArchivo", ".bin")
  tempFile.writeText(fileContent)

  val result = personalStorageBin.leerDelArchivo(tempFile)

  assertTrue(result.isOk)
  assertEquals(listOf(jugador), (result.get() as List<*>)[0]) // Basic check, more detailed might be needed
  assertEquals(entrenador.id, (result.get() as List<*>)[1].let { it as Entrenadores }.id)
  tempFile.delete()
 }

 @Test
 @DisplayName("leerDelArchivo debería retornar Err con PersonasStorageException si el archivo no existe")
 fun leerDelArchivo_shouldReturnErrWithPersonasStorageException_whenFileDoesNotExist() {
  logger.debug { "Test: leerDelArchivo debería retornar Err si el archivo no existe..." }
  val nonExistentFile = File("nonExistent.bin")
  val result = personalStorageBin.leerDelArchivo(nonExistentFile)
  assertTrue(result.isErr)
  assertTrue(result.error is PersonasException.PersonasStorageException)
  assertEquals("El fichero no existe, o no es un fichero o no se puede leer: ${nonExistentFile.path}", result.error.messager)
 }

 @Test
 @DisplayName("leerDelArchivo debería retornar Err con PersonasStorageException si el archivo no es un fichero")
 fun leerDelArchivo_shouldReturnErrWithPersonasStorageException_whenFileIsNotAFile() {
  logger.debug { "Test: leerDelArchivo debería retornar Err si el archivo no es un fichero..." }
  val tempDir = createTempDir("testLeerDelArchivoDir")
  val result = personalStorageBin.leerDelArchivo(tempDir)
  assertTrue(result.isErr)
  assertTrue(result.error is PersonasException.PersonasStorageException)
  assertEquals("El fichero no existe, o no es un fichero o no se puede leer: ${tempDir.path}", result.error.messager)
  tempDir.deleteRecursively()
 }

 @Test
 @DisplayName("leerDelArchivo debería retornar Err con PersonasStorageException si el archivo no se puede leer")
 fun leerDelArchivo_shouldReturnErrWithPersonasStorageException_whenFileCannotBeRead() {
  logger.debug { "Test: leerDelArchivo debería retornar Err si el archivo no se puede leer..." }
  val tempFile = createTempFile("testLeerDelArchivoNoLeer", ".bin")
  tempFile.setReadable(false)
  val result = personalStorageBin.leerDelArchivo(tempFile)
  assertTrue(result.isErr)
  assertTrue(result.error is PersonasException.PersonasStorageException)
  assertEquals("El fichero no existe, o no es un fichero o no se puede leer: ${tempFile.path}", result.error.messager)
  tempFile.delete()
 }

 @Test
 @DisplayName("leerDelArchivo debería retornar Err con PersonasStorageException si el archivo está vacío")
 fun leerDelArchivo_shouldReturnErrWithPersonasStorageException_whenFileIsEmpty() {
  logger.debug { "Test: leerDelArchivo debería retornar Err si el archivo está vacío..." }
  val tempFile = createTempFile("testLeerDelArchivoEmpty", ".bin")
  val result = personalStorageBin.leerDelArchivo(tempFile)
  assertTrue(result.isErr)
  assertTrue(result.error is PersonasException.PersonasStorageException)
  assertEquals("El fichero no existe, o no es un fichero o no se puede leer: ${tempFile.path}", result.error.messager)
  tempFile.delete()
 }

 @Test
 @DisplayName("leerDelArchivo debería retornar Err con PersonasStorageException si el archivo no tiene extensión .bin")
 fun leerDelArchivo_shouldReturnErrWithPersonasStorageException_whenFileExtensionIsInvalid() {
  logger.debug { "Test: leerDelArchivo debería retornar Err si la extensión del archivo es inválida..." }
  val invalidFile = File("test.txt")
  val result = personalStorageBin.leerDelArchivo(invalidFile)
  assertTrue(result.isErr)
  assertTrue(result.error is PersonasException.PersonasStorageException)
  assertEquals("El fichero no existe, o no es un fichero o no se puede leer: ${invalidFile.path}", result.error.messager)
 }

 @Test
 @DisplayName("escribirAUnArchivo debería retornar Ok con la ruta del archivo si la escritura es exitosa")
 fun escribirAUnArchivo_shouldReturnOkWithFilePath_whenWriteIsSuccessful() {
  logger.debug { "Test: escribirAUnArchivo debería retornar Ok con la ruta del archivo..." }
  val tempFile = createTempFile("testEscribirArchivo", ".bin")
  val personas = listOf(jugador, entrenador)

  val result = personalStorageBin.escribirAUnArchivo(tempFile, personas)

  assertTrue(result.isOk)
  assertEquals(tempFile.absolutePath, result.get())

  assertTrue(tempFile.exists())
  tempFile.delete()
 }

 @Test
 @DisplayName("escribirAUnArchivo debería retornar Err con PersonasStorageException si el directorio padre no existe")
 fun escribirAUnArchivo_shouldReturnErrWithPersonasStorageException_whenParentDirectoryDoesNotExist() {
  logger.debug { "Test: escribirAUnArchivo debería retornar Err si el directorio padre no existe..." }
  val nonExistentDir = File("nonExistentDir")
  val tempFile = File(nonExistentDir, "test.bin")
  val result = personalStorageBin.escribirAUnArchivo(tempFile, listOf(jugador))
  assertTrue(result.isErr)
  assertTrue(result.error is PersonasException.PersonasStorageException)
  assertEquals("El directorio padre del fichero no existe: ${nonExistentDir.absolutePath}", result.error.messager)
 }

 @Test
 @DisplayName("escribirAUnArchivo debería retornar Err con PersonasStorageException si el directorio padre no es un directorio")
 fun escribirAUnArchivo_shouldReturnErrWithPersonasStorageException_whenParentIsNotADirectory() {
  logger.debug { "Test: escribirAUnArchivo debería retornar Err si el directorio padre no es un directorio..." }
  val tempFile = createTempFile("testEscribirArchivoParentNotDir", ".bin")
  val result = personalStorageBin.escribirAUnArchivo(File(tempFile.absolutePath), listOf(jugador))
  assertTrue(result.isErr)
  assertTrue(result.error is PersonasException.PersonasStorageException)
  assertEquals("El directorio padre del fichero no existe: ${tempFile.absolutePath}", result.error.messager)
  tempFile.delete()
 }

 @Test
 @DisplayName("escribirAUnArchivo debería retornar Err con PersonasStorageException si el archivo no tiene extensión .bin")
 fun escribirAUnArchivo_shouldReturnErrWithPersonasStorageException_whenFileExtensionIsInvalid() {
  logger.debug { "Test: escribirAUnArchivo debería retornar Err si la extensión del archivo es inválida..." }
  val invalidFile = File("test.txt")
  val result = personalStorageBin.escribirAUnArchivo(invalidFile, listOf(jugador))
  assertTrue(result.isErr)
  assertTrue(result.error is PersonasException.PersonasStorageException)
  assertEquals("El directorio padre del fichero no existe: ${invalidFile.parentFile.absolutePath}", result.error.messager)
 }


 private fun createTempDir(prefix: String): File {
  val tempDir = File.createTempFile(prefix, "", File("."))
  tempDir.delete()
  tempDir.mkdirs()
  return tempDir
 }
}*/