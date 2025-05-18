package org.example.practicaenequipocristianvictoraitornico.view.controller

import org.mindrot.jbcrypt.BCrypt

data class LoginResult(val success: Boolean, val message: String)

class LoginViewModel {
    // Simulación de base de datos con BCrypt
    private val usersDB = mapOf(
        "admin" to BCrypt.hashpw("contraseñasegura", BCrypt.gensalt())
    )

    fun login(username: String, password: String): LoginResult {
        if (username.isBlank() || password.isBlank()) {
            return LoginResult(false, "¡¡Por favor, rellene todos los campos!!")
        }

        val hashedPassword = usersDB[username]
        return if (hashedPassword != null && BCrypt.checkpw(password, hashedPassword)) {
            LoginResult(true, "Inicio de sesión exitoso")
        } else {
            LoginResult(false, "Usuario o contraseña incorrectos")
        }
    }
}