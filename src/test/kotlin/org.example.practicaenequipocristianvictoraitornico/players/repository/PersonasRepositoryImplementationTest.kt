package org.example.practicaenequipocristianvictoraitornico.players.repository

import org.example.practicaenequipocristianvictoraitornico.players.dao.PersonaDao
import org.example.practicaenequipocristianvictoraitornico.players.dao.PersonaEntity
import org.example.practicaenequipocristianvictoraitornico.players.mappers.PersonaMapper
import org.example.practicaenequipocristianvictoraitornico.players.models.Entrenadores
import org.example.practicaenequipocristianvictoraitornico.players.models.Especialidad
import org.example.practicaenequipocristianvictoraitornico.players.models.Jugadores
import org.example.practicaenequipocristianvictoraitornico.players.models.Posicion
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.*
import java.time.LocalDate


import org.example.practicaenequipocristianvictoraitornico.players.models.*
import org.junit.jupiter.api.DisplayName


@ExtendWith(MockitoExtension::class)
 class PersonasRepositoryImplementationTest {
  @Mock
  private lateinit var dao: PersonaDao
  @Mock
  private lateinit var mapper: PersonaMapper
  @InjectMocks
  private lateinit var repository: org.example.practicaenequipocristianvictoraitornico.players.repository.PersonasRepositoryImplementation

  private val persona= Jugadores(
   id = 1,
   nombre = "Jugadora",
   apellidos = "hola",
   fechaNacimiento = LocalDate.parse("2020-01-01"),
   fechaIncorporacion = LocalDate.parse("2020-01-02"),
   salario = 3000.0,
   pais = "españa",
   posicion = Posicion.DEFENSA,
   dorsal = 12,
   altura = 100.0,
   peso = 100.0,
   goles = 10,
   partidosJugados = 10,
   imagen = "jaskjndkjnas"
  )
  private val persona2 = Entrenadores(
   id = 2,
   nombre = "Entrenadora",
   apellidos = "hola",
   fechaNacimiento = LocalDate.parse("2020-01-01"),
   fechaIncorporacion = LocalDate.parse("2020-01-02"),
   salario = 3000.0,
   pais = "españa",
   especialidad = Especialidad.ENTRENADOR_PORTEROS
  )
  private val persona3 = PersonaEntity(
   id = 2,
   tipo = "Entrenadores",
   nombre = "Entrenadora",
   apellidos = "hola",
   fechaNacimiento = LocalDate.parse("2020-01-01"),
   fechaIncorporacion = LocalDate.parse("2020-01-02"),
   salario = 3000.0,
   pais = "españa",
   especialidad = "ENTRENADOR_PORTEROS"
  )
  private val persona4 = PersonaEntity(
   id = 1,
   tipo = "Jugadores",
   nombre = "Jugadora",
   apellidos = "hola",
   fechaNacimiento = LocalDate.parse("2020-01-01"),
   fechaIncorporacion = LocalDate.parse("2020-01-02"),
   salario = 3000.0,
   pais = "españa",
   posicion = "DEFENSA",
   dorsal = 12,
   altura = 100.0,
   peso = 100.0,
   goles = 10,
   partidosJugados = 10,
   imagen = "jaskjndkjnas"
  )

@Test
 fun getAll() {
  whenever(dao.getAll()) doReturn listOf(persona3, persona4)
  whenever(mapper.toDatabaseModel(persona3)) doReturn persona2
  whenever(mapper.toDatabaseModel(persona4)) doReturn persona

  val result=repository.getAll()
  assertEquals(result.size,2,"debe contener dos personas")
  assertNotNull(result,"no deberia ser nulo")
  assertTrue(result.isNotEmpty())

 verify(dao,times(1)).getAll()
 verify(mapper, times(1)).toDatabaseModel(persona3)
 verify(mapper, times(1)).toDatabaseModel(persona4)
 }

@Test
@DisplayName("get id  estando esa persona")
 fun getById() {
   whenever(dao.getById(2)) doReturn persona3
   whenever(mapper.toDatabaseModel(persona3)) doReturn persona2

 val result=repository.getById(persona2.id)
 assertEquals(result,persona3,"deberia devolver la persona3")
 assertNotNull(result,"no deberia ser nulo")
 assertEquals(result!!.id,persona3.id,"deberia devolver la persona3")

 verify(dao,times(1)).getById(2)
 verify(mapper, times(1)).toDatabaseModel(persona3)
 }
 @Test
 @DisplayName("get id sin estar esa persona")
 fun getByIdBad() {
  whenever(dao.getById(3)) doReturn null

  val result=repository.getById(3.toLong())

  assertNull(result,"deberia ser nulo")

  verify(dao,times(1)).getById(3)
 }

@Test
@DisplayName("update estando bien")
 fun update() {
  whenever(dao.update(persona3,persona3.id)) doReturn 1
  whenever(mapper.toEntity(persona2)) doReturn persona3


 val result=repository.update(persona2,persona2.id)

  assertEquals(result,persona2,"deberia devolver la persona2")
  assertNotNull(result,"no deberia ser nulo")
  assertEquals(result!!.id,persona2.id,"deberia devolver la persona2")

 verify(dao,times(1)).update(persona3,persona3.id)
 verify(mapper, times(1)).toEntity(persona2)
 }
 @Test
 @DisplayName("update no estando")
 fun updateNotPersona() {
  whenever(dao.update(persona3,persona3.id)) doReturn 0
  whenever(mapper.toEntity(persona2)) doReturn persona3

  val result=repository.update(persona2,persona2.id)

  assertNull(result,"no deberia ser nulo")


  verify(dao,times(1)).update(persona3,persona3.id)
  verify(mapper, times(1)).toEntity(persona2)
 }

@Test
@DisplayName("delete persona")
 fun delete() {
  whenever(dao.getById(persona3.id)) doReturn persona3
  whenever(dao.deleteById(persona3.id)) doReturn 1
  whenever(mapper.toDatabaseModel(persona3)) doReturn persona2

 val result=repository.delete(persona2.id)

 assertEquals(result,persona2,"deberia devolver la persona2")
 assertNotNull(result,"no deberia ser nulo")
 assertEquals(result!!.id,persona2.id,"deberia devolver la persona2")

 verify(dao,times(1)).deleteById(persona3.id)
 verify(mapper, times(1)).toDatabaseModel(persona3)
 verify(dao, times(1)).getById(persona3.id)
 }
 @Test
 @DisplayName("delete no estando")
 fun deleteNotPersona() {
  whenever(dao.getById(persona3.id)) doReturn null
  whenever(mapper.toDatabaseModel(persona3)) doReturn persona2


  val result=repository.delete(persona2.id)

  assertNull(result,"deberia ser nulo")


  verify(dao,times(1)).deleteById(persona3.id)
  verify(mapper, times(0)).toDatabaseModel(persona3)
  verify(dao, times(0)).deleteById(persona3.id)
 }
 @Test
 @DisplayName("delete no estando y fallando")
 fun notDeletePersona() {
  whenever(dao.getById(persona3.id)) doReturn persona3
  whenever(mapper.toDatabaseModel(persona3)) doReturn persona2
  whenever(dao.deleteById(persona3.id)) doReturn 0


  val result=repository.delete(persona2.id)

  assertNull(result,"deberia ser nulo")


  verify(dao,times(1)).deleteById(persona3.id)
  verify(mapper, times(0)).toDatabaseModel(persona3)
  verify(dao, times(1)).deleteById(persona3.id)
 }

@Test
 fun save() {
  whenever(mapper.toEntity(persona2)).thenReturn(persona3)
  whenever(mapper.toDatabaseModel(persona3)).thenReturn(persona2)
  whenever(dao.save(persona3)) doReturn 1

 val result=repository.save(persona2)
 assertEquals(result.id,1,"deberia devolver la persona2")
 assertNotNull(result)
 assertEquals(result.nombre,persona2.nombre,"deberia devolver la persona2")
 }
}