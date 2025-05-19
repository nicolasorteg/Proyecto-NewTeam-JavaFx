package org.example.practicaenequipocristianvictoraitornico.common.repository

import org.example.practicaenequipocristianvictoraitornico.users.models.User

interface Repository<T, ID> {
    fun getAll(): List<T>
    fun getById(id: ID): T?
    fun update(objeto:T,id: ID):T?
    fun delete(id: ID):T?
    fun save(objeto: T):T
    fun getByName(username: String): User?
}