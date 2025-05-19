package org.example.practicaenequipocristianvictoraitornico.users.service

import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.*
import org.example.practicaenequipocristianvictoraitornico.users.exception.UsersException
import org.example.practicaenequipocristianvictoraitornico.users.models.User
import org.example.practicaenequipocristianvictoraitornico.users.repository.UsersRepositoryImpl
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.mindrot.jbcrypt.BCrypt
import org.lighthousegames.logging.logging
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.whenever
/*
class UsersServiceImplTest {
 private val logger = logging()
 private val repositorio = mock(UsersRepositoryImpl::class.java)
 private val service = UsersServiceImpl(repositorio)

 private val testUser = User("testUser", "password", false)
 private val testUser2 = User("testUser2", "password2", true)
 private val testException = UsersException.DatabaseException("Test Exception")

 @Test
 @DisplayName("getAll - Resultado Ok")
 fun getAll_Ok() {
  whenever(repositorio.getAll()).thenReturn(listOf(testUser, testUser2))
  val result = service.getAll()
  assertTrue(result.isOk)
  if (result.isOk) {
   assertEquals(listOf(testUser, testUser2), result.value)
  } else {
   fail("Se esperaba Ok pero se obtuvo $result")
  }

  verify(repositorio).getAll()
 }

 @Test
 @DisplayName("getAll - Resultado Err")
 fun getAll_Err() {
  whenever(repositorio.getAll()).doReturn Err(testException)
  val result = service.getAll()
  assertTrue(result.isErr)
  if (result.isErr) {
   assertEquals(testException, result.error)
  } else {
   fail("Se esperaba Err pero se obtuvo $result")
  }

  verify(repositorio).getAll()
 }

 @Test
 @DisplayName("getByID - Resultado Ok")
 fun getByID_Ok() {
  whenever(repositorio.getById("testUser")).thenReturn(testUser)
  val result = service.getByID("testUser")
  assertTrue(result.isOk)
  if (result.isOk) {
   assertEquals(testUser, result.value)
  } else {
   fail("Expected Ok result but got $result")
  }

  verify(repositorio).getById("testUser")
 }

 @Test
 @DisplayName("getByID - Resultado Err (Usuario no encontrado)")
 fun getByID_Err_NotFound() {
  whenever(repositorio.getById("nonExistentUser")).thenReturn(null)
  val result = service.getByID("nonExistentUser")
  assertTrue(result.isErr)
  if (result.isErr) {
   assertTrue(result.error is UsersException.UsersNotFoundException)
  } else {
   fail("Expected Err but got $result")
  }

  verify(repositorio).getById("nonExistentUser")
 }

 @Test
 @DisplayName("getByID - Resultado Err (Excepcion Base de datos)")
 fun getByID_Err_DatabaseException() {
  whenever(repositorio.getById(anyString())).thenThrow(testException)
  val result = service.getByID("anyId")
  assertTrue(result.isErr)
  if (result.isErr) {
   assertEquals(testException, result.error)
  } else {
   fail("Expected Err")
  }
 }

 @Test
 @DisplayName("save - Resultado Ok")
 fun save_Ok() {
  whenever(repositorio.save(testUser)).thenReturn(testUser)
  val result = service.save(testUser)
  assertTrue(result.isOk)
  if (result.isOk) {
   assertEquals(testUser, result.value)
  } else {
   fail("Expected Ok")
  }
 }

 @Test
 @DisplayName("save - Resultado Err")
 fun save_Err() {
  whenever(repositorio.save(any())).thenThrow(testException)
  val result = service.save(testUser)
  assertTrue(result.isErr)
  if (result.isErr) {
   assertEquals(testException, result.error)
  } else {
   fail("Expected Err")
  }
 }

 @Test
 @DisplayName("delete - Resultado Ok")
 fun delete_Ok() {
  whenever(repositorio.delete("testUser")).thenReturn(testUser)
  val result = service.delete("testUser")
  assertTrue(result.isOk)
  if (result.isOk) {
   assertEquals(testUser, result.value)
  } else {
   fail("Expected Ok")
  }
 }

 @Test
 @DisplayName("delete - Resultado Err (Usuario no encontrado)")
 fun delete_Err_NotFound() {
  whenever(repositorio.delete("nonExistentUser")).thenReturn(null)
  val result = service.delete("nonExistentUser")
  assertTrue(result.isErr)
  if (result.isErr) {
   assertTrue(result.error is UsersException.UsersNotFoundException)
  } else {
   fail("Expected Err")
  }
 }

 @Test
 @DisplayName("delete - Resultado Err (Excepcion Base de datos)")
 fun delete_Err_DatabaseException() {
  whenever(repositorio.delete(anyString())).thenThrow(testException)
  val result = service.delete("anyId")
  assertTrue(result.isErr)
  if (result.isErr) {
   assertEquals(testException, result.error)
  } else {
   fail("Expected Err")
  }
 }

 @Test
 @DisplayName("update - Resultado Ok")
 fun update_Ok() {
  whenever(repositorio.update(testUser, "testUser")).thenReturn(testUser)
  val result = service.update("testUser", testUser)
  assertTrue(result.isOk)
  if (result.isOk) {
   assertEquals(testUser, result.value)
  } else {
   fail("Expected Ok")
  }
 }

 @Test
 @DisplayName("update - Resultado Err (Usuario no encontrado)")
 fun update_Err_NotFound() {
  whenever(repositorio.update(testUser, "nonExistentUser")).thenReturn(null)
  val result = service.update("nonExistentUser", testUser)
  assertTrue(result.isErr)
  if (result.isErr) {
   assertTrue(result.error is UsersException.UsersNotFoundException)
  } else {
   fail("Expected Err")
  }
 }

 @Test
 @DisplayName("update - Resultado Err (Excepcion Base de datos)")
 fun update_Err_DatabaseException() {
  whenever(repositorio.update(any(), anyString())).thenThrow(testException)
  val result = service.update("anyId", testUser)
  assertTrue(result.isErr)
  if (result.isErr) {
   assertEquals(testException, result.error)
  } else {
   fail("Expected Err")
  }
 }

 @Test
 @DisplayName("goodLogin - Resultado Ok")
 fun goodLogin_Ok() {
  val hashedPassword = BCrypt.hashpw("password", BCrypt.gensalt())
  val expectedUser = testUser.copy(password = hashedPassword)

  whenever(repositorio.getById("testUser")).thenReturn(expectedUser)
  val result = service.goodLogin("testUser", "password")

  assertTrue(result.isOk)
  if (result.isOk) {
   assertEquals(expectedUser.copy(password = result.value.password), result.value)
  } else {
   fail("Expected Ok")
  }

  verify(repositorio).getById("testUser")
 }

 @Test
 @DisplayName("goodLogin - Resultado Err (ContraseÃ±a incorrecta)")
 fun goodLogin_Err_WrongPassword() {
  val wrongHash = BCrypt.hashpw("wrongPassword", BCrypt.gensalt())
  val userWithWrongPass = testUser.copy(password = wrongHash)

  whenever(repositorio.getById("testUser")).thenReturn(userWithWrongPass)
  val result = service.goodLogin("testUser", "password")

  assertTrue(result.isErr)
  if (result.isErr) {
   assertTrue(result.error is UsersException.ContraseniaEquivocadaException)
  } else {
   fail("Expected Err")
  }

  verify(repositorio).getById("testUser")
 }

 @Test
 @DisplayName("goodLogin - Resultado Err (Usuario no encontrado)")
 fun goodLogin_Err_NotFound() {
  whenever(repositorio.getById("nonExistentUser")).thenReturn(null)
  val result = service.goodLogin("nonExistentUser", "password")

  assertTrue(result.isErr)
  if (result.isErr) {
   assertTrue(result.error is UsersException.UsersNotFoundException)
  } else {
   fail("Expected Err")
  }

  verify(repositorio).getById("nonExistentUser")
 }

 @Test
 @DisplayName("goodLogin - Resultado Err (Excepcion Base de datos)")
 fun goodLogin_Err_DatabaseException() {
  whenever(repositorio.getById(anyString())).thenThrow(testException)
  val result = service.goodLogin("anyId", "password")

  assertTrue(result.isErr)
  if (result.isErr) {
   assertEquals(testException, result.error)
  } else {
   fail("Expected Err")
  }

  verify(repositorio).getById("anyId")
 }
}*/