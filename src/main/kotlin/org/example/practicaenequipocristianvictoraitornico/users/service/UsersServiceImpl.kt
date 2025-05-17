package org.example.practicaenequipocristianvictoraitornico.users.service

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result

import org.example.practicaenequipocristianvictoraitornico.users.exception.UsersException
import org.example.practicaenequipocristianvictoraitornico.users.models.User
import org.example.practicaenequipocristianvictoraitornico.users.repository.UsersRepositoryImpl


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
        return repositorio.getById(id)?.let { Ok(it) }?:
        Err(UsersException.UsersNotFoundException(id))
    }

    override fun save(item: User): Result<User, UsersException> {
        return Ok(repositorio.save(item))
    }

    override fun delete(id: String): Result<User, UsersException> {
        return repositorio.delete(id)?.let { Ok(it) }?:
        Err(UsersException.UsersNotFoundException(id))
    }

    override fun update(id: String, item:User): Result<User, UsersException> {
        return repositorio.update(item,id)?.let { Ok(it) }?:
        Err(UsersException.UsersNotFoundException(id))
    }
}