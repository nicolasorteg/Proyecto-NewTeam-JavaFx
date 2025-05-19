package org.example.practicaenequipocristianvictoraitornico.view.controller

import com.github.michaelbull.result.Result
import org.example.practicaenequipocristianvictoraitornico.users.exception.UsersException
import org.example.practicaenequipocristianvictoraitornico.users.models.User
import org.example.practicaenequipocristianvictoraitornico.users.service.UsersServiceImpl
import org.mindrot.jbcrypt.BCrypt

data class LoginResult(val success: Boolean, val message: String)

class LoginViewModel(
    private val users: UsersServiceImpl
) {
    // Simulación de base de datos con BCrypt
    private lateinit var loginResult: User

    fun login(username: String, password: String): Result<User,UsersException> {

        //val crypt= BCrypt.hashpw(password,BCrypt.gensalt(12))

        val login= users.goodLogin(username,password)
        if (login.isOk){
            loginResult=login.value
        }
        return login
    }
}