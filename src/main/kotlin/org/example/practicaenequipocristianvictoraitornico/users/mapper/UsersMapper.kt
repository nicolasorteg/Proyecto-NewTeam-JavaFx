package org.example.practicaenequipocristianvictoraitornico.users.mapper


import org.example.practicaenequipocristianvictoraitornico.users.dao.UsersEntity
import org.example.practicaenequipocristianvictoraitornico.users.models.User

class UsersMapper {
    fun toEntity(user: User): UsersEntity {
        return UsersEntity(
            name = user.name,
            password = user.password,
            admin = user.admin,
        )
    }
    fun toModel(user:UsersEntity): User {
        return User(
            name = user.name,
            password = user.password,
            admin = user.admin,
        )
    }
}