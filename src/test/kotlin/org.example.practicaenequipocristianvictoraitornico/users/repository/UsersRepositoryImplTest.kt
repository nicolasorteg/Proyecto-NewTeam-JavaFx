package org.example.practicaenequipocristianvictoraitornico.users.repository

import org.example.practicaenequipocristianvictoraitornico.users.dao.UsersDao
import org.example.practicaenequipocristianvictoraitornico.users.dao.UsersEntity
import org.example.practicaenequipocristianvictoraitornico.users.mapper.UsersMapper
import org.example.practicaenequipocristianvictoraitornico.users.models.User
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.lighthousegames.logging.logging
import org.mockito.kotlin.whenever

class UsersRepositoryImplTest {
 private val logger = logging()
 private val usersDao = mock(UsersDao::class.java)
 private val mapper = mock(UsersMapper::class.java)
 private val usersRepository = UsersRepositoryImpl(usersDao, mapper)

 @Test
 @DisplayName("Test de getAll: devuelve una lista de usuarios.")
 fun getAll() {
  logger.debug { "Probando getAll: debería devolver una lista de usuarios..." }
  val usersEntities = listOf(
   UsersEntity(name = "User1", password = "pass1", admin = false),
   UsersEntity(name = "User2", password = "pass2", admin = true)
  )
  val usersModels = listOf(
   User(name = "User1", password = "pass1", admin = false),
   User(name = "User2", password = "pass2", admin = true)
  )

  whenever(usersDao.getAll()).thenReturn(usersEntities)
  whenever(mapper.toModel(usersEntities[0])).thenReturn(usersModels[0])
  whenever(mapper.toModel(usersEntities[1])).thenReturn(usersModels[1])

  val result = usersRepository.getAll()

  assertEquals(usersModels, result)
  verify(usersDao, times(1)).getAll()
  verify(mapper, times(2)).toModel(any())
 }

 @Test
 @DisplayName("Test de getById: devuelve un usuario existente.")
 fun getById() {
  logger.debug { "Probando getById: debería devolver un usuario existente..." }
  val entity = UsersEntity(name = "TestUser", password = "testPass", admin = false)
  val model = User(name = "TestUser", password = "testPass", admin = false)
  val id = "TestUser"

  whenever(usersDao.getByName(id)).thenReturn(entity)
  whenever(mapper.toModel(entity)).thenReturn(model)

  val result = usersRepository.getById(id)

  assertEquals(model, result)
  verify(usersDao, times(1)).getByName(id)
  verify(mapper, times(1)).toModel(entity)
 }

 @Test
 @DisplayName("Test de getById: devuelve null si el usuario no existe.")
 fun getById_Null() {
  logger.debug { "Probando getById: debería devolver null si el usuario no existe..." }
  val id = "NonExistentUser"

  whenever(usersDao.getByName(id)).thenReturn(null)

  val result = usersRepository.getById(id)

  assertNull(result)
  verify(usersDao, times(1)).getByName(id)
  verify(mapper, never()).toModel(any())
 }

 @Test
 @DisplayName("Test de update: actualiza un usuario existente y devuelve el usuario actualizado.")
 fun update() {
  logger.debug { "Probando update: debería actualizar un usuario y devolver el usuario actualizado..." }
  val initialUser = User(name = "OldName", password = "oldPass", admin = false)
  val updatedUser = User(name = "NewName", password = "newPass", admin = true)
  val updatedEntity = UsersEntity(name = "NewName", password = "newPass", admin = true)
  val id = "NewName"

  whenever(mapper.toEntity(updatedUser)).thenReturn(updatedEntity)
  whenever(usersDao.update(updatedEntity, id)).thenReturn(1)

  val result = usersRepository.update(updatedUser, id)

  assertEquals(updatedUser.copy(name = id), result)
  verify(mapper, times(1)).toEntity(updatedUser)
  verify(usersDao, times(1)).update(updatedEntity, id)
 }

 @Test
 @DisplayName("Test de update: devuelve null si el usuario no se actualiza.")
 fun update_Null() {
  logger.debug { "Probando update: debería devolver null si el usuario no se actualiza..." }
  val userToUpdate = User(name = "Test", password = "pass", admin = false)
  val entityToUpdate = UsersEntity(name = "Test", password = "pass", admin = false)
  val id = "Test"

  whenever(mapper.toEntity(userToUpdate)).thenReturn(entityToUpdate)
  whenever(usersDao.update(entityToUpdate, id)).thenReturn(0)

  val result = usersRepository.update(userToUpdate, id)

  assertNull(result)
  verify(mapper, times(1)).toEntity(userToUpdate)
  verify(usersDao, times(1)).update(entityToUpdate, id)
 }

 @Test
 @DisplayName("Test de delete: elimina un usuario existente y devuelve el usuario eliminado.")
 fun delete() {
  logger.debug { "Probando delete: debería eliminar un usuario y devolver el usuario eliminado..." }
  val entityToDelete = UsersEntity(name = "ToDelete", password = "delPass", admin = false)
  val modelToDelete = User(name = "ToDelete", password = "delPass", admin = false)
  val id = "ToDelete"

  whenever(usersDao.getByName(id)).thenReturn(entityToDelete)
  whenever(mapper.toModel(entityToDelete)).thenReturn(modelToDelete)
  doNothing().whenever(usersDao).delete(id)

  val result = usersRepository.delete(id)

  assertEquals(modelToDelete, result)
  verify(usersDao, times(1)).getByName(id)
  verify(mapper, times(1)).toModel(entityToDelete)
  verify(usersDao, times(1)).delete(id)
 }

 @Test
 @DisplayName("Test de delete: devuelve null si el usuario a eliminar no existe.")
 fun delete_Null() {
  logger.debug { "Probando delete: debería devolver null si el usuario a eliminar no existe..." }
  val id = "NonExistentToDelete"

  whenever(usersDao.getByName(id)).thenReturn(null)

  val result = usersRepository.delete(id)

  assertNull(result)
  verify(usersDao, times(1)).getByName(id)
  verify(mapper, never()).toModel(any())
  verify(usersDao, never()).delete(anyString())
 }

 @Test
 @DisplayName("Test de save: guarda un nuevo usuario y lo devuelve.")
 fun save() {
  logger.debug { "Probando save: debería guardar un nuevo usuario y devolverlo..." }
  val userToSave = User(name = "NewUser", password = "newPass", admin = true)
  val entityToSave = UsersEntity(name = "NewUser", password = "newPass", admin = true)

  whenever(mapper.toEntity(userToSave)).thenReturn(entityToSave)
  doNothing().whenever(usersDao).save(entityToSave)

  val result = usersRepository.save(userToSave)

  assertEquals(userToSave, result)
  verify(mapper, times(1)).toEntity(userToSave)
  verify(usersDao, times(1)).save(entityToSave)
 }
}