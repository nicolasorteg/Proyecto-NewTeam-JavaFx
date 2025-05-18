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
import org.mockito.Mockito.*
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.io.File
import java.time.LocalDate

class PersonalStorageCsvTest {

 private val personalStorageCsv = PersonalStorageCsv()
 private val mockFile = mock(File::class.java)

 private val jugadorCsvLine = "1,Cristiano,Ronaldo,1985-02-05,2023-08-15,100000.0,Portugal,Jugador,,DELANTERO,7,1.87,84.0,700,900,url"
 private val entrenadorCsvLine = "2,Zinedine,Zidane,1972-06-23,2022-05-10,80000.0,Francia,Entrenador,ATAQUE,,,,,,,"

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
  whenever(mockFile.isFile).thenReturn(false)
  whenever(mockFile.exists()).thenReturn(false)
  whenever(mockFile.canRead()).thenReturn(false)

  val result = personalStorageCsv.leerDelArchivo(mockFile)

  assertTrue(result.isErr)
  assertTrue(result.error is PersonasException.PersonasStorageException)
  assertEquals("El fichero no existe o no se puede leer: Mock for File, hashCode: ${mockFile.hashCode()}", result.error.message)
 }

 @Test
 @DisplayName("leerDelArchivo debería retornar Err con PersonasStorageException si el archivo no es un fichero")
 fun leerDelArchivo_retornaErrConPersonasStorageException_archivoNoEsFichero() {
  whenever(mockFile.isFile).thenReturn(false)
  whenever(mockFile.exists()).thenReturn(true)
  whenever(mockFile.canRead()).thenReturn(true)

  val result = personalStorageCsv.leerDelArchivo(mockFile)

  assertTrue(result.isErr)
  assertTrue(result.error is PersonasException.PersonasStorageException)
  assertEquals("El fichero no existe o no se puede leer: Mock for File, hashCode: ${mockFile.hashCode()}", result.error.message)
 }

 @Test
 @DisplayName("leerDelArchivo debería retornar Err con PersonasStorageException si el archivo no se puede leer")
 fun leerDelArchivo_retornaErrConPersonasStorageException_archivoNoSePuedeLeer() {
  whenever(mockFile.isFile).thenReturn(true)
  whenever(mockFile.exists()).thenReturn(true)
  whenever(mockFile.canRead()).thenReturn(false)

  val result = personalStorageCsv.leerDelArchivo(mockFile)

  assertTrue(result.isErr)
  assertTrue(result.error is PersonasException.PersonasStorageException)
  assertEquals("El fichero no existe o no se puede leer: Mock for File, hashCode: ${mockFile.hashCode()}", result.error.message)
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
  assertTrue(fileContent.contains(entrenadorCsvLine.replace(",,,,,,,", ",DELANTERO,7,1.87,84.0,700,900,url"))) // Adjust entrenador line for extra commas
  tempFile.delete()
 }

 @Test
 @DisplayName("escribirAUnArchivo debería retornar Err con PersonasStorageException si el directorio padre no existe")
 fun escribirAUnArchivo_retornaErrConPersonasStorageException_directorioPadreNoExiste() {
  whenever(mockFile.parentFile).thenReturn(File("nonExistentDir"))
  whenever(mockFile.parentFile?.exists()).thenReturn(false)
  whenever(mockFile.name).thenReturn("test.csv")

  val result = personalStorageCsv.escribirAUnArchivo(mockFile, listOf(jugador))

  assertTrue(result.isErr)
  assertTrue(result.error is PersonasException.PersonasStorageException)
  assertEquals("No se puede escribir en el archivo debido a que no existe o no es de la extensión adecuada 😔", result.error.message)
 }

 @Test
 @DisplayName("escribirAUnArchivo debería retornar Err con PersonasStorageException si el archivo no tiene extensión .csv")
 fun escribirAUnArchivo_retornaErrConPersonasStorageException_extensionArchivoInvalida() {
  whenever(mockFile.parentFile).thenReturn(File("."))
  whenever(mockFile.parentFile?.exists()).thenReturn(true)
  whenever(mockFile.name).thenReturn("test.txt")

  val result = personalStorageCsv.escribirAUnArchivo(mockFile, listOf(jugador))

  assertTrue(result.isErr)
  assertTrue(result.error is PersonasException.PersonasStorageException)
  assertEquals("No se puede escribir en el archivo debido a que no existe o no es de la extensión adecuada 😔", result.error.message)
 }

 @Test
 @DisplayName("escribirAUnArchivo debería retornar Err con PersonasStorageException si la persona es de un tipo desconocido")
 fun escribirAUnArchivo_retornaErrConPersonasStorageException_tipoPersonaDesconocido() {
  val personaDesconocida = object : Persona() {}
  val tempFile = File("test.csv")

  val result = personalStorageCsv.escribirAUnArchivo(tempFile, listOf(personaDesconocida))

  assertTrue(result.isErr)
  assertTrue(result.error is PersonasException.PersonasStorageException)
  assertEquals("Tipo de persona desconocido: Persona\$1", result.error.message)
 }
}