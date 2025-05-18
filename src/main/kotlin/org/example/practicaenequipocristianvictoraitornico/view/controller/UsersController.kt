package org.example.practicaenequipocristianvictoraitornico.view.controller

import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.stage.Stage

import org.example.practicaenequipocristianvictoraitornico.users.service.UsersServiceImpl
import org.lighthousegames.logging.logging
import org.mindrot.jbcrypt.BCrypt
import org.example.practicaenequipocristianvictoraitornico.view.routes.RoutesManager

class UsersController(
    private val users: UsersServiceImpl
) {

    private val logger = logging()


    @FXML
    private lateinit var userName: TextField

    @FXML
    private lateinit var password: PasswordField

    @FXML
    private lateinit var loginButton: Button

    @FXML
    private lateinit var loginMessage: Label

    @FXML
    fun initialize() {
        loginButton.setOnAction {
            logger.debug { "Iniciar loggin" }
            login()
        }
    }

    private fun login() {
        val inputUser = userName.text.trim()
        val inputPass = password.text

        if (inputUser.isBlank() || inputPass.isBlank()) {
            loginMessage.text = "¡¡Por favor, rellene todos los campos!!"
            loginMessage.style = "-fx-text-fill: red;"
            return
        }
        val crypt= BCrypt.hashpw(inputPass,BCrypt.gensalt(12))


        if (inputPass != null && users.goodLogin(inputUser,crypt).isOk) {
            loginMessage.text = "Inicio de sesión exitoso"
            loginMessage.style = "-fx-text-fill: green;"
            logger.info { "Usuario $inputUser ha iniciado sesión correctamente" }

            // Mostramos la vista principal desde RoutesManager
            RoutesManager.showMainView()

            // Cerramos la ventana actual (login)
            (loginMessage.scene.window as Stage).close()
        } else {
            loginMessage.text = "Usuario o contraseña incorrectos"
            loginMessage.style = "-fx-text-fill: red;"
            logger.info { "Intento fallido de login para el usuario: $inputUser" }
        }
    }
}
