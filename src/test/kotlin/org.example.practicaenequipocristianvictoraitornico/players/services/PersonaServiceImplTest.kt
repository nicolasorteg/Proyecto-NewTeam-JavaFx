package org.example.practicaenequipocristianvictoraitornico.players.services

import com.github.benmanes.caffeine.cache.Cache
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import org.example.practicaenequipocristianvictoraitornico.players.exception.PersonasException

import org.example.practicaenequipocristianvictoraitornico.players.models.Jugadores
import org.example.practicaenequipocristianvictoraitornico.players.models.Persona
import org.example.practicaenequipocristianvictoraitornico.players.models.Posicion
import org.example.practicaenequipocristianvictoraitornico.players.repository.PersonasRepositoryImplementation
import org.example.practicaenequipocristianvictoraitornico.players.storage.PersonalStorageZip
import org.example.practicaenequipocristianvictoraitornico.players.storage.Tipo
import org.example.practicaenequipocristianvictoraitornico.players.validator.PersonaValidation
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.io.File
import java.nio.file.Path
import java.time.LocalDate


@ExtendWith(MockitoExtension::class)
 class PersonaServiceImplTest {
  @Mock
  private lateinit var repository: PersonasRepositoryImplementation
  @Mock
  private lateinit var cache: Cache<Long, Persona>
  @Mock
  private lateinit var validator: PersonaValidation
  @Mock
  private lateinit var storage: PersonalStorageZip
  @InjectMocks
  private lateinit var service: PersonaServiceImpl

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
 private val lista=listOf<Persona>(persona)
 private val file= File("xd.zip")



@Test
@DisplayName("importacion correctamente de jugador")
 fun importarDatosDesdeFichero() {
 whenever(repository.save(persona)) doReturn persona
 whenever(validator.validator(persona)) doReturn Ok(persona)
 whenever(storage.leerDelArchivo(file)) doReturn Ok(lista)


 val result= service.importarDatosDesdeFichero(Path.of("xd.zip"))
 assertEquals(persona, result.value.first(),"deben ser iguales")
 assertTrue(result.value.size == 1)
 assertTrue(result.isOk)
 verify(repository, times(1)).save(persona)
 verify(validator, times(1)).validator(persona)
 verify(storage, times(1)).leerDelArchivo(file)

 }

 @Test
 @DisplayName("importacion fallida de jugador")
 fun importarDatosDesdeFicheroDatoNombreIncorrecto() {
  //whenever(repository.save(persona)) doReturn persona
  whenever(validator.validator(persona)) doReturn Err(PersonasException.PersonasInvalidoException("nombre invalida"))
  whenever(storage.leerDelArchivo(file)) doReturn Ok(lista)


  val result= service.importarDatosDesdeFichero(Path.of("xd.zip"))
  assertTrue(result.error is PersonasException.PersonasInvalidoException, "debe tener ese error")
  assertTrue(result.isErr,"debe ser un error")
  assertEquals(result.error.messager,"Persona no válida: nombre invalida","deben ser iguales")
  verify(validator, times(1)).validator(persona)
  verify(storage, times(1)).leerDelArchivo(file)

 }

@Test
@DisplayName("exportacion correctamente de jugador")
 fun exportarDatosDesdeFichero() {
  val path= Path.of("xd.zip")
  whenever(storage.escribirAUnArchivo(path.toFile(),lista, Tipo.JSON)) doReturn Ok("guardado con exito")
  whenever(repository.getAll()) doReturn lista
  val result=service.exportarDatosDesdeFichero(path,Tipo.JSON)
  assertTrue(result.isOk,"debe devolver Ok")
  assertEquals(result.value,"guardado con exito","devuelve el mensaje")
 verify(repository, times(1)).getAll()
 verify(storage, times(1)).escribirAUnArchivo(file,lista, Tipo.JSON)
 }

@Test
 fun getAll() {
  whenever(repository.getAll()) doReturn lista
  val result = service.getAll()
 assertEquals(lista.size,result.value.size,"deben ser iguales")
 assertTrue(result.value.isNotEmpty(),"debe estar llena")
 verify(repository, times(1)).getAll()
 }

@Test
 fun getByID() {}

@Test
 fun save() {}

@Test
 fun delete() {}

@Test
 fun update() {}
}