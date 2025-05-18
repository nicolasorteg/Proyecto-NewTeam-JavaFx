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
import org.mockito.Mockito.*
import org.lighthousegames.logging.logging
import org.mockito.kotlin.*
import org.mockito.kotlin.eq
import java.io.File
import java.io.RandomAccessFile
import java.time.LocalDate

class PersonalStorageBinTest {
 private val logger = logging()
 private val personaMapper = mock(PersonaMapper::class.java)
 private val personalStorageBin = PersonalStorageBin() // No podemos mockear la clase bajo prueba directamente

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
  especialidad = Especialidad.ENTRENADOR_PRINCIPAL,
 )

 private val jugadorDto = JugadorDto(
  1L, "Cristiano", "Ronaldo", "1985-02-05", LocalDate.now().toString(), 100000.0, "Portugal",
  "DELANTERO", 7, 1.87, 84.0, 700, 900, "url"
 )
 private val entrenadorDto = EntrenadorDto(
  2L, "Zinedine", "Zidane", "1972-06-23", LocalDate.now().toString(), 80000.0, "Francia", "ATAQUE"
 )

 private val mockFile = mock(File::class.java)
 private val nonExistentFile = File("nonExistent.bin")
 private val invalidFile = File("invalid.txt")
 private val emptyFile = File("empty.bin")
 private val mockParentDir = mock(File::class.java)

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

  whenever(personaMapper.toModel(eq(jugadorDto))).thenReturn(jugador)
  whenever(personaMapper.toModel(eq(entrenadorDto))).thenReturn(entrenador)

  val result = personalStorageBin.leerDelArchivo(tempFile)

  assertTrue(result.isOk)
  assertEquals(listOf(jugador, entrenador), result.get())
  tempFile.delete()
 }

 @Test
 @DisplayName("leerDelArchivo debería retornar Err con PersonasStorageException si el archivo no existe")
 fun leerDelArchivo_shouldReturnErrWithPersonasStorageException_whenFileDoesNotExist() {
  logger.debug { "Test: leerDelArchivo debería retornar Err si el archivo no existe..." }
  val result = personalStorageBin.leerDelArchivo(nonExistentFile)
  assertTrue(result.isErr)
  assertTrue(result.error is PersonasException.PersonasStorageException)
  assertEquals("El fichero no existe, o no es un fichero o no se puede leer: ${nonExistentFile.path}", result.error.message)
 }

 @Test
 @DisplayName("leerDelArchivo debería retornar Err con PersonasStorageException si el archivo no es un fichero")
 fun leerDelArchivo_shouldReturnErrWithPersonasStorageException_whenFileIsNotAFile() {
  logger.debug { "Test: leerDelArchivo debería retornar Err si el archivo no es un fichero..." }
  whenever(mockFile.exists()).thenReturn(true)
  whenever(mockFile.isFile).thenReturn(false)
  whenever(mockFile.canRead()).thenReturn(true)
  whenever(mockFile.name).thenReturn("test.bin")
  val result = personalStorageBin.leerDelArchivo(mockFile)
  assertTrue(result.isErr)
  assertTrue(result.error is PersonasException.PersonasStorageException)
  assertEquals("El fichero no existe, o no es un fichero o no se puede leer: Mock for File, hashCode: ${mockFile.hashCode()}", result.error.message)
 }

 @Test
 @DisplayName("leerDelArchivo debería retornar Err con PersonasStorageException si el archivo no se puede leer")
 fun leerDelArchivo_shouldReturnErrWithPersonasStorageException_whenFileCannotBeRead() {
  logger.debug { "Test: leerDelArchivo debería retornar Err si el archivo no se puede leer..." }
  whenever(mockFile.exists()).thenReturn(true)
  whenever(mockFile.isFile()).thenReturn(true)
  whenever(mockFile.canRead()).thenReturn(false)
  whenever(mockFile.name).thenReturn("test.bin")
  val result = personalStorageBin.leerDelArchivo(mockFile)
  assertTrue(result.isErr)
  assertTrue(result.error is PersonasException.PersonasStorageException)
  assertEquals("El fichero no existe, o no es un fichero o no se puede leer: Mock for File, hashCode: ${mockFile.hashCode()}", result.error.message)
 }

 @Test
 @DisplayName("leerDelArchivo debería retornar Err con PersonasStorageException si el archivo está vacío")
 fun leerDelArchivo_shouldReturnErrWithPersonasStorageException_whenFileIsEmpty() {
  logger.debug { "Test: leerDelArchivo debería retornar Err si el archivo está vacío..." }
  whenever(mockFile.exists()).thenReturn(true)
  whenever(mockFile.isFile()).thenReturn(true)
  whenever(mockFile.canRead()).thenReturn(true)
  whenever(mockFile.length()).thenReturn(0L)
  whenever(mockFile.name).thenReturn("test.bin")
  val result = personalStorageBin.leerDelArchivo(mockFile)
  assertTrue(result.isErr)
  assertTrue(result.error is PersonasException.PersonasStorageException)
  assertEquals("El fichero no existe, o no es un fichero o no se puede leer: Mock for File, hashCode: ${mockFile.hashCode()}", result.error.message)
 }

 @Test
 @DisplayName("leerDelArchivo debería retornar Err con PersonasStorageException si el archivo no tiene extensión .bin")
 fun leerDelArchivo_shouldReturnErrWithPersonasStorageException_whenFileExtensionIsInvalid() {
  logger.debug { "Test: leerDelArchivo debería retornar Err si la extensión del archivo es inválida..." }
  whenever(mockFile.exists()).thenReturn(true)
  whenever(mockFile.isFile()).thenReturn(true)
  whenever(mockFile.canRead()).thenReturn(true)
  whenever(mockFile.length()).thenReturn(10L)
  whenever(mockFile.name).thenReturn("test.txt")
  val result = personalStorageBin.leerDelArchivo(mockFile)
  assertTrue(result.isErr)
  assertTrue(result.error is PersonasException.PersonasStorageException)
  assertEquals("El fichero no existe, o no es un fichero o no se puede leer: Mock for File, hashCode: ${mockFile.hashCode()}", result.error.message)
 }

 @Test
 @DisplayName("escribirAUnArchivo debería retornar Ok con la ruta del archivo si la escritura es exitosa")
 fun escribirAUnArchivo_shouldReturnOkWithFilePath_whenWriteIsSuccessful() {
  logger.debug { "Test: escribirAUnArchivo debería retornar Ok con la ruta del archivo..." }
  whenever(mockFile.parentFile).thenReturn(mockParentDir)
  whenever(mockParentDir.exists()).thenReturn(true)
  whenever(mockParentDir.isDirectory).thenReturn(true)
  whenever(mockFile.name).thenReturn("test.bin")

  val result = personalStorageBin.escribirAUnArchivo(mockFile, listOf(jugador, entrenador))

  assertTrue(result.isOk)
  assertEquals(mockFile.absolutePath, result.get())
 }

 @Test
 @DisplayName("escribirAUnArchivo debería retornar Err con PersonasStorageException si el directorio padre no existe")
 fun escribirAUnArchivo_shouldReturnErrWithPersonasStorageException_whenParentDirectoryDoesNotExist() {
  logger.debug { "Test: escribirAUnArchivo debería retornar Err si el directorio padre no existe..." }
  whenever(mockFile.parentFile).thenReturn(mockParentDir)
  whenever(mockParentDir.exists()).thenReturn(false)

  val result = personalStorageBin.escribirAUnArchivo(mockFile, listOf(jugador))

  assertTrue(result.isErr)
  assertTrue(result.error is PersonasException.PersonasStorageException)
  assertEquals("El directorio padre del fichero no existe: Mock for File, hashCode: ${mockParentDir.hashCode()}", result.error.message)
 }

 @Test
 @DisplayName("escribirAUnArchivo debería retornar Err con PersonasStorageException si el directorio padre no es un directorio")
 fun escribirAUnArchivo_shouldReturnErrWithPersonasStorageException_whenParentIsNotADirectory() {
  logger.debug { "Test: escribirAUnArchivo debería retornar Err si el directorio padre no es un directorio..." }
  whenever(mockFile.parentFile).thenReturn(mockParentDir)
  whenever(mockParentDir.exists()).thenReturn(true)
  whenever(mockParentDir.isDirectory).thenReturn(false)

  val result = personalStorageBin.escribirAUnArchivo(mockFile, listOf(jugador))

  assertTrue(result.isErr)
  assertTrue(result.error is PersonasException.PersonasStorageException)
  assertEquals("El directorio padre del fichero no existe: Mock for File, hashCode: ${mockParentDir.hashCode()}", result.error.message)
 }

 @Test
 @DisplayName("escribirAUnArchivo debería retornar Err con PersonasStorageException si el archivo no tiene extensión .bin")
 fun escribirAUnArchivo_shouldReturnErrWithPersonasStorageException_whenFileExtensionIsInvalid() {
  logger.debug { "Test: escribirAUnArchivo debería retornar Err si la extensión del archivo es inválida..." }
  whenever(mockFile.parentFile).thenReturn(mockParentDir)
  whenever(mockParentDir.exists()).thenReturn(true)
  whenever(mockParentDir.isDirectory).thenReturn(true)
  whenever(mockFile.name).thenReturn("test.txt")

  val result = personalStorageBin.escribirAUnArchivo(mockFile, listOf(jugador))

  assertTrue(result.isErr)
  assertTrue(result.error is PersonasException.PersonasStorageException)
  assertEquals("El directorio padre del fichero no existe: Mock for File, hashCode: ${mockParentDir.hashCode()}", result.error.message)
 }
}