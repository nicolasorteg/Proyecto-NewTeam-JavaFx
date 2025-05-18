package org.example.practicaenequipocristianvictoraitornico.players.validator

import org.example.practicaenequipocristianvictoraitornico.players.models.Jugadores
import org.example.practicaenequipocristianvictoraitornico.players.models.Posicion
import org.example.practicaenequipocristianvictoraitornico.players.validator.PersonaValidation
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.time.LocalDate
import org.lighthousegames.logging.logging


class PersonaValidationTest {
 private val logger = logging()
 private val validator = PersonaValidation()

 @Test
 @DisplayName("Test de pasar el validador correctamente.")
 fun validarJugadorValido(){

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

  assertTrue(result.isOk, "El validador debe devolver Ok")
  assertEquals(jugador, result.value, "El validador debe devolver el mismo jugador")
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

 @Test
 @DisplayName("Test de apellidos cortos.")
 fun validarApellidosCortos() {

  logger.debug { "Validando jugador con apellidos cortos..." }

  val jugador = Jugadores (
   id = 1,
   nombre = "Cristian",
   apellidos = "O",
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
  assertEquals("Persona no válida: Apellidos inválidos, los apellidos son demasiado cortos.", result.error.messager)
 }

 @Test
 @DisplayName("Test de salarios negativos o nulos.")
 fun validarSalarioBajo() {

  logger.debug { "Validando jugador con salario negativo/nulo..." }

  val jugador = Jugadores (
   id = 1,
   nombre = "Cristian",
   apellidos = "Ortega",
   salario = -50.0,
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
  assertEquals("Persona no válida: Salario inválido, el salario no puede ser igual o menor a 0.", result.error.messager)
 }

 @Test
 @DisplayName("Test de pais vacío.")
 fun validarPaisVacio() {

  logger.debug { "Validando jugador con país vacío..." }

  val jugador = Jugadores (
   id = 1,
   nombre = "Cristian",
   apellidos = "Ortega",
   salario = 1200.0,
   pais = "",
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
  assertEquals("Persona no válida: País inválido, este campo no puede estar vacío.", result.error.messager)
 }

 @Test
 @DisplayName("Test de pais corto.")
 fun validarPaisCorto() {

  logger.debug { "Validando jugador con país corto..." }

  val jugador = Jugadores (
   id = 1,
   nombre = "Cristian",
   apellidos = "Ortega",
   salario = 1200.0,
   pais = "E",
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
  assertEquals("Persona no válida: País inválido, el país es demasiado corto.", result.error.messager)
 }

 @Test
 @DisplayName("Test de dorsal negativo o 0.")
 fun validarDorsalBajo() {

  logger.debug { "Validando jugador con dorsal negativo/0..." }

  val jugador = Jugadores (
   id = 1,
   nombre = "Cristian",
   apellidos = "Ortega",
   salario = 1200.0,
   pais = "España",
   fechaNacimiento = LocalDate.of(2003,8,20),
   fechaIncorporacion = LocalDate.of(2025, 5,16),
   imagen = "https://www.hdwallpapers.in/download/cristiano_ronaldo_cr7_with_cup_in_blur_stadium_background_is_wearing_white_sports_dress_hd_cristiano_ronaldo-HD.jpg",
   posicion = Posicion.CENTROCAMPISTA,
   dorsal = -3,
   altura = 1.75,
   peso = 60.0,
   goles = 35,
   partidosJugados = 30,
  )

  val result = validator.validator(jugador)

  assertTrue(result.isErr)
  assertEquals("Persona no válida: Dorsal inválido, el dorsal no puede ser igual o inferior a 0.", result.error.messager)
 }

 @Test
 @DisplayName("Test de dorsal superior a 99.")
 fun validarDorsalAlto() {

  logger.debug { "Validando jugador con dorsal demasiado alto (+99)..." }

  val jugador = Jugadores (
   id = 1,
   nombre = "Cristian",
   apellidos = "Ortega",
   salario = 1200.0,
   pais = "España",
   fechaNacimiento = LocalDate.of(2003,8,20),
   fechaIncorporacion = LocalDate.of(2025, 5,16),
   imagen = "https://www.hdwallpapers.in/download/cristiano_ronaldo_cr7_with_cup_in_blur_stadium_background_is_wearing_white_sports_dress_hd_cristiano_ronaldo-HD.jpg",
   posicion = Posicion.CENTROCAMPISTA,
   dorsal = 333,
   altura = 1.75,
   peso = 60.0,
   goles = 35,
   partidosJugados = 30,
  )

  val result = validator.validator(jugador)

  assertTrue(result.isErr)
  assertEquals("Persona no válida: Dorsal inválido, el dorsal no puede ser mayor a 99.", result.error.messager)
 }

 @Test
 @DisplayName("Test de altura demasiado baja.")
 fun validarAlturaBaja() {

  logger.debug { "Validando jugador con poca altura..." }

  val jugador = Jugadores (
   id = 1,
   nombre = "Cristian",
   apellidos = "Ortega",
   salario = 1200.0,
   pais = "España",
   fechaNacimiento = LocalDate.of(2003,8,20),
   fechaIncorporacion = LocalDate.of(2025, 5,16),
   imagen = "https://www.hdwallpapers.in/download/cristiano_ronaldo_cr7_with_cup_in_blur_stadium_background_is_wearing_white_sports_dress_hd_cristiano_ronaldo-HD.jpg",
   posicion = Posicion.CENTROCAMPISTA,
   dorsal = 8,
   altura = 0.7,
   peso = 60.0,
   goles = 35,
   partidosJugados = 30,
  )

  val result = validator.validator(jugador)

  assertTrue(result.isErr)
  assertEquals("Persona no válida: Altura inválida, el jugador no puede ser tan bajo.", result.error.messager)
 }

 @Test
 @DisplayName("Test de altura demasiado alta.")
 fun validarAlturaAlta() {

  logger.debug { "Validando jugador con mucha altura..." }

  val jugador = Jugadores (
   id = 1,
   nombre = "Cristian",
   apellidos = "Ortega",
   salario = 1200.0,
   pais = "España",
   fechaNacimiento = LocalDate.of(2003,8,20),
   fechaIncorporacion = LocalDate.of(2025, 5,16),
   imagen = "https://www.hdwallpapers.in/download/cristiano_ronaldo_cr7_with_cup_in_blur_stadium_background_is_wearing_white_sports_dress_hd_cristiano_ronaldo-HD.jpg",
   posicion = Posicion.CENTROCAMPISTA,
   dorsal = 8,
   altura = 5.0,
   peso = 60.0,
   goles = 35,
   partidosJugados = 30,
  )

  val result = validator.validator(jugador)

  assertTrue(result.isErr)
  assertEquals("Persona no válida: Altura inválida, el jugador no puede ser tan alto.", result.error.messager)
 }

 @Test
 @DisplayName("Test de peso insuficiente.")
 fun validarPesoBajo() {

  logger.debug { "Validando jugador con peso insuficiente..." }

  val jugador = Jugadores (
   id = 1,
   nombre = "Cristian",
   apellidos = "Ortega",
   salario = 1200.0,
   pais = "España",
   fechaNacimiento = LocalDate.of(2003,8,20),
   fechaIncorporacion = LocalDate.of(2025, 5,16),
   imagen = "https://www.hdwallpapers.in/download/cristiano_ronaldo_cr7_with_cup_in_blur_stadium_background_is_wearing_white_sports_dress_hd_cristiano_ronaldo-HD.jpg",
   posicion = Posicion.CENTROCAMPISTA,
   dorsal = 8,
   altura = 1.6,
   peso = 15.0,
   goles = 35,
   partidosJugados = 30,
  )

  val result = validator.validator(jugador)

  assertTrue(result.isErr)
  assertEquals("Persona no válida: Peso inválido, necesita comer más.", result.error.messager)
 }

 @Test
 @DisplayName("Test de peso excesivo.")
 fun validarPesoAlto() {

  logger.debug { "Validando jugador con peso excesivo..." }

  val jugador = Jugadores (
   id = 1,
   nombre = "Cristian",
   apellidos = "Ortega",
   salario = 1200.0,
   pais = "España",
   fechaNacimiento = LocalDate.of(2003,8,20),
   fechaIncorporacion = LocalDate.of(2025, 5,16),
   imagen = "https://www.hdwallpapers.in/download/cristiano_ronaldo_cr7_with_cup_in_blur_stadium_background_is_wearing_white_sports_dress_hd_cristiano_ronaldo-HD.jpg",
   posicion = Posicion.CENTROCAMPISTA,
   dorsal = 8,
   altura = 1.6,
   peso = 333.0,
   goles = 35,
   partidosJugados = 30,
  )

  val result = validator.validator(jugador)

  assertTrue(result.isErr)
  assertEquals("Persona no válida: Peso inválido, necesita comer menos.", result.error.messager)
 }

 @Test
 @DisplayName("Test de goles negativos.")
 fun validarGolesNegativos() {

  logger.debug { "Validando jugador con goles negativos..." }

  val jugador = Jugadores (
   id = 1,
   nombre = "Cristian",
   apellidos = "Ortega",
   salario = 1200.0,
   pais = "España",
   fechaNacimiento = LocalDate.of(2003,8,20),
   fechaIncorporacion = LocalDate.of(2025, 5,16),
   imagen = "https://www.hdwallpapers.in/download/cristiano_ronaldo_cr7_with_cup_in_blur_stadium_background_is_wearing_white_sports_dress_hd_cristiano_ronaldo-HD.jpg",
   posicion = Posicion.CENTROCAMPISTA,
   dorsal = 8,
   altura = 1.6,
   peso = 60.0,
   goles = -5,
   partidosJugados = 30,
  )

  val result = validator.validator(jugador)

  assertTrue(result.isErr)
  assertEquals("Persona no válida: Goles inválido, no puede tener goles negativos.", result.error.messager)
 }

 @Test
 @DisplayName("Test de partidos jugados negativos.")
 fun validarPartidosJugadosNegativos() {

  logger.debug { "Validando jugador con goles negativos..." }

  val jugador = Jugadores (
   id = 1,
   nombre = "Cristian",
   apellidos = "Ortega",
   salario = 1200.0,
   pais = "España",
   fechaNacimiento = LocalDate.of(2003,8,20),
   fechaIncorporacion = LocalDate.of(2025, 5,16),
   imagen = "https://www.hdwallpapers.in/download/cristiano_ronaldo_cr7_with_cup_in_blur_stadium_background_is_wearing_white_sports_dress_hd_cristiano_ronaldo-HD.jpg",
   posicion = Posicion.CENTROCAMPISTA,
   dorsal = 8,
   altura = 1.6,
   peso = 60.0,
   goles = 33,
   partidosJugados = -4,
  )

  val result = validator.validator(jugador)

  assertTrue(result.isErr)
  assertEquals("Persona no válida: Partidos jugados inválidos, no puede jugar partidos negativos.", result.error.messager)
 }
}