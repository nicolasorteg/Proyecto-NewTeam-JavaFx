package org.example.practicaenequipocristianvictoraitornico.view.routes
import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.stage.Modality
import javafx.stage.Stage
import javafx.util.Duration
import javafx.animation.PauseTransition
import javafx.application.Platform
import javafx.scene.control.Alert
import javafx.scene.control.ButtonType

import javafx.scene.layout.Pane
import javafx.stage.WindowEvent

import org.example.practicaenequipocristianvictoraitornico.view.acercaDe.AcercaDeController

import org.lighthousegames.logging.logging

import java.io.InputStream


import java.net.URL
import java.util.*

val logger = logging()

object RoutesManager {

    private lateinit var mainStage: Stage
    private lateinit var _activeStage: Stage
    val activeStage: Stage get() = _activeStage
    lateinit var app: Application

    enum class View(val fxml: String) {
        MAIN("/org/example/practicaenequipocristianvictoraitornico/view/players/principal-view.fxml"),
        ACERCA_DE("/org/example/practicaenequipocristianvictoraitornico/view/acerca-de/acercaDe-view.fxml"),
        SPLASH_SCREEN("/org/example/practicaenequipocristianvictoraitornico/view/splash-screen/splash-screen-view.fxml"),
        TARJETAS_JUGADORES("/org/example/practicaenequipocristianvictoraitornico/view/players/tarjetasJugadores-view.fxml"),
        USER("/org/example/practicaenequipocristianvictoraitornico/view/user/user-view.fxml"),
    }

    init {
        logger.debug { "Inicializando RoutesManager" }
        Locale.setDefault(Locale.forLanguageTag("es-ES"))
    }

    /**
     * Inicializa la aplicación mostrando la pantalla splash.
     */
    fun initApp(stage: Stage) {
        logger.debug { "Iniciando aplicación con pantalla Splash" }


        mainStage = stage
        _activeStage = stage

        // Cargamos el FXML del Splash
        val loader = FXMLLoader(getResource(View.SPLASH_SCREEN.fxml))
        val root = loader.load<Pane>()
        val scene = Scene(root)

        // Configuramos la ventana
        stage.apply {
            title = "Cargando aplicación..."
            scene.root = root
            isResizable = false
            this.scene = scene
            centerOnScreen()
            show()
        }

        // Esperamos 3 segundos, luego mostramos el login
        val pause = PauseTransition(Duration.seconds(5.0))
        pause.play()
    }

    /**
     * Muestra la ventana de inicio de sesión.
     */
    fun showLogin(owner: Stage) {
        logger.debug { "Mostrando pantalla de login" }

        val loader = FXMLLoader(getResource(View.USER.fxml))
        val root = loader.load<Pane>()
        val scene = Scene(root)

        val loginStage = Stage().apply {
            title = "Iniciar sesión"
            this.scene = scene
            isResizable = false
            initOwner(owner)
            initModality(Modality.APPLICATION_MODAL)
            centerOnScreen()
        }

        _activeStage.close()
        _activeStage = loginStage
        loginStage.show()
    }

    /**
     * Muestra la vista principal de la aplicación.
     */
    fun showMainView() {
        logger.debug { "Mostrando vista principal" }

        val loader = FXMLLoader(getResource(View.MAIN.fxml))
        val root = loader.load<Pane>()
        val scene = Scene(root)

        mainStage.apply {
            title = "Gestor del New Team"
            this.scene = scene
            isResizable = false
            setOnCloseRequest { onAppExit(it.toString()) }
            centerOnScreen()
            show()
        }

        _activeStage.close()
        _activeStage = mainStage
    }

    /**
     * Abre una ventana modal con información "Acerca De".
     */
    fun showAcercaDe() {
        logger.debug { "Mostrando ventana Acerca De" }

        val loader = FXMLLoader(getResource(View.ACERCA_DE.fxml))
        val root = loader.load<Pane>()
        val controller = loader.getController<AcercaDeController>()
        val scene = Scene(root)

        Stage().apply {
            title = "Acerca del programa"
            this.scene = scene
            isResizable = false
            initOwner(mainStage)
            initModality(Modality.WINDOW_MODAL)
            centerOnScreen()
        }.show()
    }

    /**
     * Abre una ventana con la vista de datos de los jugadores.
     */
    fun showTarjetasJugadores() {
        logger.debug { "Mostrando ventana Datos Jugadores" }

        val loader = FXMLLoader(getResource(View.TARJETAS_JUGADORES.fxml))
        val root = loader.load<Pane>()
        val scene = Scene(root)

        Stage().apply {
            title = "Datos de Jugadores"
            this.scene = scene
            isResizable = false
            initOwner(mainStage)
            initModality(Modality.WINDOW_MODAL)
            centerOnScreen()
        }.show()
    }

    /**
     * Cierra la aplicación con confirmación.
     */
    fun onAppExit(
        title: String = "¿Salir de la aplicación?",
        headerText: String = "¿Estás seguro de que deseas salir?",
        contentText: String = "Si sales ahora, se perderán los cambios no guardados.",
        event: WindowEvent? = null
    ) {
        logger.debug { "Solicitud de salida de la aplicación" }

        Alert(Alert.AlertType.CONFIRMATION).apply {
            this.title = title
            this.headerText = headerText
            this.contentText = contentText
        }.showAndWait().ifPresent { result ->
            if (result == ButtonType.OK) {
                Platform.exit()
            } else {
                event?.consume()
            }
        }
    }

    /**
     * Utilidad para obtener recursos como URL.
     */
    fun getResource(resource: String): URL {
        return app::class.java.getResource(resource)
            ?: throw RuntimeException("Recurso no encontrado: $resource")
    }

    /**
     * Utilidad para obtener recursos como InputStream.
     */
    fun getResourceAsStream(resource: String): InputStream {
        return app::class.java.getResourceAsStream(resource)
            ?: throw RuntimeException("Recurso no encontrado como stream: $resource")
    }
}
