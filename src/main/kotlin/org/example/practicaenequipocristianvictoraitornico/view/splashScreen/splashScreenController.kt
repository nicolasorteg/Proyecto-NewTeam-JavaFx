package org.example.practicaenequipocristianvictoraitornico.view.splashScreen

import javafx.fxml.FXML
import javafx.scene.Scene
import javafx.scene.control.Label
import javafx.scene.control.ProgressBar
import javafx.stage.Stage
import org.example.practicaenequipocristianvictoraitornico.view.routes.RoutesManager

class SplashScreenController {

    @FXML
    private lateinit var mensajeCarga: Label

    @FXML
    private lateinit var barraCarga: ProgressBar

    private val viewModel = SplashScreenViewModel()

    fun initialize() {
        // Bind de propiedades
        mensajeCarga.textProperty().bind(viewModel.mensajeCarga)
        barraCarga.progressProperty().bind(viewModel.progresoCarga)

        // Iniciar carga simulada
        viewModel.iniciarCarga {
            val stage = barraCarga.scene.window as Stage
            RoutesManager.showLogin(stage)
        }
    }
}