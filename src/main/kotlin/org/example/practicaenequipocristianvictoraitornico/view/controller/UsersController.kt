package org.example.practicaenequipocristianvictoraitornico.view.controller

import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.stage.Stage
import org.lighthousegames.logging.logging
import org.example.practicaenequipocristianvictoraitornico.view.routes.RoutesManager
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class UsersController : KoinComponent {
    private val logger = logging()

    // Inyección del ViewModel
    val usersController: LoginViewModel by inject()

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

        if (usersController.login(inputUser,inputPass).isOk) {
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