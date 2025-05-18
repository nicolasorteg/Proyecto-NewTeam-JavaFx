package org.example.practicaenequipocristianvictoraitornico

import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.ComboBox
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.TilePane
import javafx.scene.layout.VBox
import org.example.practicaenequipocristianvictoraitornico.players.models.Posicion

import org.example.practicaenequipocristianvictoraitornico.view.routes.RoutesManager
import org.example.practicaenequipocristianvictoraitornico.view.routes.logger
import java.util.*

class PrincipalController {
    @FXML
    private lateinit var btnJugadores: Button

    @FXML
    private lateinit var btnEntrenadores: Button

    @FXML
    private lateinit var estadisticasButton: Button

    @FXML
    private lateinit var anchorpane: AnchorPane

    @FXML
    private lateinit var tilePaneTarjetas: TilePane

    @FXML
    private lateinit var hboxposicion: VBox

    @FXML
    private lateinit var PosicionLabel: Label

    @FXML
    private lateinit var NewTeamlabel: Label

    @FXML
    private lateinit var LadoIzquierdo: VBox

    @FXML
    private lateinit var comboBoxPosicion: ComboBox<String>

    @FXML
    private lateinit var acercaDeButton: Button

    @FXML
    private lateinit var salirButton: Button

    @FXML
    private lateinit var miembrosText: TextField

    @FXML
    private lateinit var mediaGolesText: TextField

    @FXML
    fun initialize() {
        logger.debug { "Inicializando PrincipalController" }

        /**
         * Se inserta la enum class Posicion de los jugadores en el ComboBox y se selecciona una opcion.
         */
        comboBoxPosicion.items.addAll(Posicion.values().map { it.name.capitalize() })
        comboBoxPosicion.setOnAction {
            val posicion = comboBoxPosicion.value
            logger.debug { "Posicion seleccionada: $posicion" }
        }

        /**
         * El boton salir, llama a RoutesManager que tiene la funcion onAppExit, que sirve para cerrar con alerta.
         */
        salirButton.setOnAction { RoutesManager.onAppExit() }

        /**
         * El boton acerca de, llama a RoutesManager que tiene la funcion showAcercaDe que sirve para abir la ventana acercaDe
         */
        acercaDeButton.setOnAction { RoutesManager.showAcercaDe() }

        /**
         *El boton btnJugadores, llama a RoutesManager que tiene la funcion showTarjetasJugadores que sirve para abrir la ventana de las tarjetas de los jugadores.
         */
        btnJugadores.setOnAction { RoutesManager.showTarjetasJugadores() }
    }
}