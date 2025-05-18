package org.example.practicaenequipocristianvictoraitornico.users.service

import com.github.michaelbull.result.*

import org.example.practicaenequipocristianvictoraitornico.users.exception.UsersException
import org.example.practicaenequipocristianvictoraitornico.users.models.User
import org.example.practicaenequipocristianvictoraitornico.users.repository.UsersRepositoryImpl
import org.lighthousegames.logging.logging
import org.mindrot.jbcrypt.BCrypt

private val logger = logging()
class UsersServiceImpl(
    private val repositorio: UsersRepositoryImpl,
): UsersService {
    override fun getAll(): Result<List<User>,UsersException> {
        try {
            return Ok(repositorio.getAll())
        }catch (e:Exception){
            return Err(UsersException.DatabaseException(e.message.toString()))
        }

    }


    override fun getByID(id: String): Result<User, UsersException> {

        try {
            return repositorio.getById(id)?.let { Ok(it) } ?: Err(UsersException.UsersNotFoundException(id))
        }catch (e:Exception){
            return Err(UsersException.DatabaseException(e.message.toString()))
        }
    }

    override fun save(item: User): Result<User, UsersException> {
        try {
            return Ok(repositorio.save(item))
        }catch (e:Exception){
            return Err(UsersException.DatabaseException(e.message.toString()))
        }

    }

    override fun delete(id: String): Result<User, UsersException> {
        try {
            return repositorio.delete(id)?.let { Ok(it) }?:
            Err(UsersException.UsersNotFoundException(id))
        }catch (e:Exception){
            return Err(UsersException.DatabaseException(e.message.toString()))
        }

    }

    override fun update(id: String, item:User): Result<User, UsersException> {
        try {
            return repositorio.update(item,id)?.let { Ok(it) }?:
            Err(UsersException.UsersNotFoundException(id))
        }catch (e:Exception){
            return Err(UsersException.DatabaseException(e.message.toString()))
        }
    }
    fun goodLogin(username: String, password: String): Result<User, UsersException> {
        return getByID(username).onSuccess {
            val user = it
            return if(BCrypt.checkpw(it.password,password)) Ok(user) else Err(UsersException.ContraseniaEquivocadaException("Contraseña errónea"))
        }.onFailure {
            return Err(it)
        }

    }
}