package org.example.practicaenequipocristianvictoraitornico.view.splashScreen

import javafx.application.Platform
import javafx.beans.property.DoubleProperty
import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleStringProperty
import javafx.beans.property.StringProperty
import kotlin.concurrent.thread

class SplashScreenViewModel {

    val mensajeCarga: StringProperty = SimpleStringProperty("Cargando...")
    val progresoCarga: DoubleProperty = SimpleDoubleProperty(0.0)

    fun iniciarCarga(onComplete: () -> Unit) {
        thread {
            for (i in 0..100) {
                Thread.sleep(50)
                val progress = i / 100.0
                Platform.runLater {
                    progresoCarga.set(progress)
                    mensajeCarga.set("Cargando... $i%")
                    if (progress >= 1.0) {
                        onComplete()
                    }
                }
            }
        }
    }
}
