package org.example.practicaenequipocristianvictoraitornico

import javafx.application.Application
import javafx.stage.Stage
import org.example.practicaenequipocristianvictoraitornico.di.appModule
import org.example.practicaenequipocristianvictoraitornico.view.routes.RoutesManager
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin
import java.time.LocalDateTime

class MainApp : Application(), KoinComponent {
    init {
        println(LocalDateTime.now().toString())
        // creamos Koin
        startKoin {
            printLogger() // Logger de Koin
            modules(appModule) // Módulos de Koin
        }
    }
    override fun start(primaryStage: Stage) {
        RoutesManager.apply {
            app= this@MainApp
        }.run { initApp(primaryStage) }
    }
}

fun main() {
    Application.launch(MainApp::class.java)
}