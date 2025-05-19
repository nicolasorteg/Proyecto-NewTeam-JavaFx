package org.example.practicaenequipocristianvictoraitornico.users.mapper

import org.example.practicaenequipocristianvictoraitornico.users.dao.UsersEntity
import org.example.practicaenequipocristianvictoraitornico.users.models.User
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.lighthousegames.logging.logging

class UsersMapperTest {
 private val logger = logging()
 private val mapper = UsersMapper()

 @Test
 @DisplayName("Test de conversión de User a UsersEntity.")
 fun toEntity() {
  logger.debug { "Probando la conversión de User a UsersEntity..." }
  val user = User(name = "TestUser", password = "password123", admin = false)
  val entity = mapper.toEntity(user)
  assertEquals(user.name, entity.name)
  assertEquals(user.password, entity.password)
  assertEquals(user.admin, entity.admin)
 }

 @Test
 @DisplayName("Test de conversión de UsersEntity a User.")
 fun toModel() {
  logger.debug { "Probando la conversión de UsersEntity a User..." }
  val entity = UsersEntity(name = "AnotherUser", password = "securePass", admin = true)
  val model = mapper.toModel(entity)
  assertEquals(entity.name, model.name)
  assertEquals(entity.password, model.password)
  assertEquals(entity.admin, model.admin)
 }
}