package org.example.practicaenequipocristianvictoraitornico.view.tarjetasJugadores

import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.TextField
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.stage.Stage
import org.example.practicaenequipocristianvictoraitornico.players.models.Jugadores

class TarjetasJugadoresController {
    @FXML
    private lateinit var imagenJugadorCarta: ImageView

    @FXML
    private lateinit var nombreJugadorCarta: TextField

    @FXML
    private lateinit var posicionJugadorCarta: TextField

    @FXML
    private lateinit var dorsalJugadorCarta: TextField

    @FXML
    private lateinit var golesJugadorCarta: TextField

    @FXML
    private lateinit var partidosJugadorCarta: TextField

    @FXML
    private lateinit var botonVolverCarta: Button

    fun setJugador(jugador: Jugadores) {
        nombreJugadorCarta.text = jugador.nombre
        posicionJugadorCarta.text = jugador.posicion.toString()
        dorsalJugadorCarta.text = jugador.dorsal.toString()
        golesJugadorCarta.text = jugador.goles.toString()
        partidosJugadorCarta.text = jugador.partidosJugados.toString()
        imagenJugadorCarta.image = Image(javaClass.getResource(jugador.imagen)?.toExternalForm())

        botonVolverCarta.setOnAction {
            val stage = botonVolverCarta.scene.window as Stage
            stage.close()
        }
    }
}
