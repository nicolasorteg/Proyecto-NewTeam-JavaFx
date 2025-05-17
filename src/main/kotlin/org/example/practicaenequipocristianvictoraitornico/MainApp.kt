package org.example.practicaenequipocristianvictoraitornico

import javafx.application.Application
import javafx.stage.Stage
import org.example.practicaenequipocristianvictoraitornico.view.routes.RoutesManager

class MainApp : Application() {
    override fun start(primaryStage: Stage) {
        RoutesManager.initApp( primaryStage, this)
    }
}

fun main() {
    Application.launch(MainApp::class.java)
}