package org.example.practicaenequipocristianvictoraitornico.view.splashScreen

import javafx.animation.PauseTransition
import javafx.application.Application
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.scene.control.Label
import javafx.scene.image.Image
import javafx.stage.Stage
import javafx.util.Duration

class SplashScreenController {

    @FXML
    private lateinit var mensajeCarga: Label

    fun setStatus(message: String) {
        mensajeCarga.text = message
    }
}