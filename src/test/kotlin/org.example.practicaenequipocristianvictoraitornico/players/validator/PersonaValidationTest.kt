package players.validator

import org.example.practicaenequipocristianvictoraitornico.players.exception.PersonasException
import org.example.practicaenequipocristianvictoraitornico.players.exception.PersonasException.*
import org.example.practicaenequipocristianvictoraitornico.players.models.Jugadores
import org.example.practicaenequipocristianvictoraitornico.players.models.Persona
import org.example.practicaenequipocristianvictoraitornico.players.models.Posicion
import org.example.practicaenequipocristianvictoraitornico.players.validator.PersonaValidation
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.time.LocalDate
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import org.lighthousegames.logging.logging


class PersonasValidadorKtTest {
 private val logger = logging()
 val validator = PersonaValidation()

 @Test
 @DisplayName("Test de pasar el validador correctamente.")
 fun validarOk(){

  logger.debug { "Validando jugador correcto..." }

  val jugador = Jugadores (
   id = 1,
   nombre = "Cristian",
   apellidos = "Ortega",
   salario = 12000.0,
   pais = "España",
   fechaNacimiento = LocalDate.of(2003,8,20),
   fechaIncorporacion = LocalDate.of(2025, 5,16),
   imagen = "https://www.hdwallpapers.in/download/cristiano_ronaldo_cr7_with_cup_in_blur_stadium_background_is_wearing_white_sports_dress_hd_cristiano_ronaldo-HD.jpg",
   posicion = Posicion.CENTROCAMPISTA,
   dorsal = 8,
   altura = 1.75,
   peso = 60.0,
   goles = 35,
   partidosJugados = 30,
  )

  val result = validator.validator(jugador)

  assertEquals(jugador, result)
 }

 @Test
 @DisplayName("Test de nombre en blanco.")
 fun validarNombreVacio() {

  logger.debug { "Validando jugador sin nombre..." }

  val jugador = Jugadores (
   id = 1,
   nombre = "",
   apellidos = "Ortega",
   salario = 12000.0,
   pais = "España",
   fechaNacimiento = LocalDate.of(2003,8,20),
   fechaIncorporacion = LocalDate.of(2025, 5,16),
   imagen = "https://www.hdwallpapers.in/download/cristiano_ronaldo_cr7_with_cup_in_blur_stadium_background_is_wearing_white_sports_dress_hd_cristiano_ronaldo-HD.jpg",
   posicion = Posicion.CENTROCAMPISTA,
   dorsal = 8,
   altura = 1.75,
   peso = 60.0,
   goles = 35,
   partidosJugados = 30,
  )

  val result = validator.validator(jugador)

  assertTrue(result.isErr)
  assertEquals("Persona no válida: Nombre inválido, este campo no puede estar vacío.", result.error.messager)
 }

 @Test
 @DisplayName("Test de nombre corto.")
 fun validarNombreCorto() {

  logger.debug { "Validando jugador con nombre corto..." }

  val jugador = Jugadores (
   id = 1,
   nombre = "C",
   apellidos = "Ortega",
   salario = 12000.0,
   pais = "España",
   fechaNacimiento = LocalDate.of(2003,8,20),
   fechaIncorporacion = LocalDate.of(2025, 5,16),
   imagen = "https://www.hdwallpapers.in/download/cristiano_ronaldo_cr7_with_cup_in_blur_stadium_background_is_wearing_white_sports_dress_hd_cristiano_ronaldo-HD.jpg",
   posicion = Posicion.CENTROCAMPISTA,
   dorsal = 8,
   altura = 1.75,
   peso = 60.0,
   goles = 35,
   partidosJugados = 30,
  )
  val result = validator.validator(jugador)
  assertTrue(result.isErr)
  assertEquals("Persona no válida: Nombre inválido, el nombre es demasiado corto.", result.error.messager)
 }

 @Test
 @DisplayName("Test de apellidos vacíos.")
 fun validarApellidosVacios() {

  logger.debug { "Validando jugador sin apellidos..." }

  val jugador = Jugadores (
   id = 1,
   nombre = "Cristian",
   apellidos = "",
   salario = 12000.0,
   pais = "España",
   fechaNacimiento = LocalDate.of(2003,8,20),
   fechaIncorporacion = LocalDate.of(2025, 5,16),
   imagen = "https://www.hdwallpapers.in/download/cristiano_ronaldo_cr7_with_cup_in_blur_stadium_background_is_wearing_white_sports_dress_hd_cristiano_ronaldo-HD.jpg",
   posicion = Posicion.CENTROCAMPISTA,
   dorsal = 8,
   altura = 1.75,
   peso = 60.0,
   goles = 35,
   partidosJugados = 30,
  )

  val result = validator.validator(jugador)

  assertTrue(result.isErr)
  assertEquals("Persona no válida: Apellidos inválidos, este campo no puede estar vacío.", result.error.messager)
 }
}