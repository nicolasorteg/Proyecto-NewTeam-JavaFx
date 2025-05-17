package org.example.practicaenequipocristianvictoraitornico.players.mappers

import org.example.practicaenequipocristianvictoraitornico.players.dao.PersonaEntity
import org.example.practicaenequipocristianvictoraitornico.players.dto.EntrenadorDto
import org.example.practicaenequipocristianvictoraitornico.players.dto.JugadorDto
import org.example.practicaenequipocristianvictoraitornico.players.dto.PersonalDtoXml
import org.example.practicaenequipocristianvictoraitornico.players.models.*
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import java.time.LocalDate


class PersonaMapperTest {
  private val mapper = PersonaMapper()
  private val jugador= Jugadores(
 id = 1,
 nombre = "Jugador",
 apellidos = "ajska",
 fechaIncorporacion = LocalDate.of(2021,1,1),
 fechaNacimiento =LocalDate.of(2021,1,1),
 salario = 1000.0,
 pais = "España",
 posicion = Posicion.PORTERO,
 dorsal = 1,
 altura = 100.0,
 peso = 100.0,
 goles = 3,
 partidosJugados = 1,
 imagen = "jhsdhahds"
 )
 private val jugadordto= JugadorDto(
  id = 1,
  nombre = "Jugador",
  apellidos = "ajska",
  fechaIncorporacion = LocalDate.of(2021,1,1).toString(),
  fechaNacimiento =LocalDate.of(2021,1,1).toString(),
  salario = 1000.0,
  pais = "España",
  posicion = "PORTERO",
  dorsal = 1,
  altura = 100.0,
  peso = 100.0,
  goles = 3,
  partidosJugados = 1,
  imagen = "jhsdhahds"
 )
 private val jugadorentt =PersonaEntity(
  id = 1,
  tipo = "Jugador",
  nombre = "Jugador",
  apellidos = "ajska",
  fechaIncorporacion = LocalDate.of(2021,1,1),
  fechaNacimiento =LocalDate.of(2021,1,1),
  salario = 1000.0,
  pais = "España",
  posicion = "PORTERO",
  dorsal = 1,
  altura = 100.0,
  peso = 100.0,
  goles = 3,
  partidosJugados = 1,
  imagen = "jhsdhahds"
 )
private val entrenador= Entrenadores(
 id = 1,
 nombre = "Entrenador",
 apellidos = "ajska",
 fechaIncorporacion = LocalDate.of(2021,1,1),
 fechaNacimiento = LocalDate.of(2021,1,1),
 salario = 1000.0,
 pais = "España",
 especialidad = Especialidad.ENTRENADOR_ASISTENTE
)
 private val entrenadorDto= EntrenadorDto(
  id = 1,
  nombre = "Entrenador",
  apellidos = "ajska",
  fechaIncorporacion = LocalDate.of(2021,1,1).toString(),
  fechaNacimiento = LocalDate.of(2021,1,1).toString(),
  salario = 1000.0,
  pais = "España",
  especialidad = "ENTRENADOR_ASISTENTE"
 )
 private val entrenadorEntity= PersonaEntity(
  id = 1,
  tipo = "Entrenador",
  nombre = "Entrenador",
  apellidos = "ajska",
  fechaIncorporacion = LocalDate.of(2021,1,1),
  fechaNacimiento = LocalDate.of(2021,1,1),
  salario = 1000.0,
  pais = "España",
  especialidad = "ENTRENADOR_ASISTENTE"
 )


@Test
@DisplayName("siendo un jugador")
 fun jugadorToDto() {
  val result=mapper.toDto(jugador)
  assertEquals(result,jugadordto,"deberian ser iguales")
  assertTrue(result is JugadorDto,"deberia ser un JugadorDTO")
 }

 @Test
 @DisplayName("siendo un Entrenador")
 fun entrenadorToDto() {
  val result=mapper.toDto(entrenador)
  assertEquals(result,entrenadorDto,"deberian ser iguales")
  assertTrue(result is EntrenadorDto,"deberia ser un EntenadorDTO")
 }
 @Test
 @DisplayName("siendo otra cosa")
 fun personaToDto(){

 }
@Test
@DisplayName("entrenador dto a modelo")
 fun entrenadorToModel() {
  val result=mapper.toModel(entrenadorDto)
  assertEquals(result,entrenador,"deberian ser iguales")
  assertTrue(result is Entrenadores,"deberia ser un entrenador")
 }
 @Test
 @DisplayName("jugador dto a modelo")
 fun jugadorToModel() {
  val result=mapper.toModel(jugadordto)
  assertEquals(result,jugador,"deberian ser iguales")
  assertTrue(result is Jugadores,"deberia ser un jugador")
 }

@Test
@DisplayName("entrenadorEntity a modelo")
 fun entrenadorToDatabaseModel() {
  val result=mapper.toDatabaseModel(entrenadorEntity)
  assertEquals(result,entrenador,"deberia ser iguales")
  assertTrue(result is Entrenadores,"deberia ser un Entrenador")
 }

 @Test
 @DisplayName("jugadorEntity a modelo")
 fun jugadorToDatabaseModel() {
  val result=mapper.toDatabaseModel(jugadorentt)
  assertEquals(result,jugador,"deberia ser iguales")
  assertTrue(result is Jugadores,"deberia ser un Jugador")
 }
@Test
@DisplayName("jugador a Entity")
 fun jugadorToEntity() {
  val result=mapper.toEntity(jugador)
 assertEquals(result,jugadorentt,"deberia ser iguales")
 assertTrue(result is PersonaEntity,"deberia ser un PersonaEntity")
 }

@Test
@DisplayName("entrenador a Entity")
fun entrenadorToEntity() {
 val result=mapper.toEntity(entrenador)
 assertEquals(result,entrenadorEntity,"deberia ser iguales")
 assertTrue(result is PersonaEntity,"deberia ser un PersonaEntity")
}
}