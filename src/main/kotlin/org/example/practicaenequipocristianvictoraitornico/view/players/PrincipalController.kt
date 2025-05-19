package org.example.practicaenequipocristianvictoraitornico.view.players

import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.scene.Cursor.DEFAULT
import javafx.scene.Cursor.WAIT
import javafx.scene.control.*
import javafx.scene.control.Alert.AlertType
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.TilePane
import javafx.scene.layout.VBox
import javafx.stage.FileChooser

import org.example.practicaenequipocristianvictoraitornico.players.models.Persona
import org.example.practicaenequipocristianvictoraitornico.players.models.Posicion
import org.example.practicaenequipocristianvictoraitornico.view.routes.RoutesManager
import org.example.practicaenequipocristianvictoraitornico.view.routes.logger
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class PrincipalController: KoinComponent {
    @FXML
    lateinit var PosicionLabel: Label

    @FXML
    lateinit var tilePaneTarjetas: TilePane

    @FXML
    lateinit var anchorpane: AnchorPane

    @FXML
    lateinit var estadisticasButton: Button
    @FXML
    lateinit var NewTeamlabel: Label

    @FXML
    lateinit var LadoIzquierdo: VBox

    @FXML
    lateinit var detallesButtom: Button

    @FXML
    lateinit var importarButtom: Button

    @FXML
    lateinit var exportarButtom: Button

    private val viewModel: PersonasViewModel by inject()

    @FXML
    private lateinit var btnJugadores: Button



    @FXML
    private lateinit var tablePlayers: TableView<Persona>

    @FXML
    private lateinit var tableColumnNumero: TableColumn<Persona, Long>

    @FXML
    private lateinit var tableColumNombre: TableColumn<Persona, String>

    @FXML
    private lateinit var tableColumnGoles: TableColumn<Persona, Double>

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
        initDefaultValues()
        initBindings()
        initEventos()
    }

    private fun initEventos() {
        /**
         * Se inserta la enum class Posicion de los jugadores en el ComboBox y se selecciona una opcion.
         */
        comboBoxPosicion.items.addAll(Posicion.values().map { it.name.capitalize() })
        comboBoxPosicion.setOnAction {
            val posicion = comboBoxPosicion.value
            logger.debug { "Posicion seleccionada: $posicion" }
        }
        /**
         * exporta a un fichero zip
         */
        exportarButtom.setOnAction { onExportarAction() }
        /**
         * importa de un fichero
         */
        importarButtom.setOnAction { onImportarAction() }
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

    }

    private fun onImportarAction(){
        logger.debug { "Importando PrincipalController" }
        FileChooser().run {
            title = "Importando Datos"
            extensionFilters.add(FileChooser.ExtensionFilter("ZIP", "*.zip"))
            showOpenDialog(RoutesManager.activeStage)
        }?.let { logger.debug { "File chosen: $it" }
        showAlertOperacion(Alert.AlertType.INFORMATION,
        "importando Datos",
            "las personas sin imagen se sustituyen la imagen por una por defecto")
        RoutesManager.activeStage.scene.cursor = WAIT
        viewModel.LoadPersonas(it)}
    }

    private fun onExportarAction() {
        logger.debug { "Exportando datos" }
        FileChooser().run {
            title = "Exportando datos"
            extensionFilters.add(FileChooser.ExtensionFilter("JSON", "*.json"))
            extensionFilters.add(FileChooser.ExtensionFilter("CSV", "*.csv"))
            extensionFilters.add(FileChooser.ExtensionFilter("BIN", "*.bin"))
            extensionFilters.add(FileChooser.ExtensionFilter("XML", "*.xml"))
            showSaveDialog(RoutesManager.activeStage)
        }?.let{
            logger.debug { "guardando datos: $it" }
            RoutesManager.activeStage.scene.cursor = WAIT
            viewModel.savePersonas(it).onSuccess {
                showAlertOperacion(
                    title = "guardado con exito",
                    mensaje = "se han guardado el personal"
                )
            }.onFailure { error->
                showAlertOperacion(
                    alerta = AlertType.ERROR,
                    title = "error al exportar",
                    mensaje = error.messager
                )
            }
            RoutesManager.activeStage.scene.cursor = DEFAULT
        }
    }


    private fun initBindings() {
        mediaGolesText.textProperty().bind(viewModel.state.map { it.mediaGoles })
        miembrosText.textProperty().bind(viewModel.state.map { it.numeroPersonas })
    }

    private fun initDefaultValues() {
        logger.debug { "Inicializando valores por defecto" }
        comboBoxPosicion.selectionModel.selectFirst()
        tablePlayers.items  = FXCollections.observableArrayList(viewModel.state.value.lista)
        tableColumnNumero.cellValueFactory= PropertyValueFactory("id")
        tableColumNombre.cellValueFactory= PropertyValueFactory("nombreCompleto")
        tableColumnGoles.cellValueFactory= PropertyValueFactory("salario")
        viewModel.state.addListener { _, _, newValue ->
            logger.debug { "Actualizando datos de la vista" }
            if (tablePlayers.items != newValue.lista) {
                tablePlayers.items = FXCollections.observableArrayList(newValue.lista)
            }
        }
        }
    fun showAlertOperacion(
        alerta: AlertType = AlertType.INFORMATION,
        title: String,
        mensaje: String,
        header: String? = null
    ) {
        Alert(alerta).apply {
            this.title = title
            this.headerText = header
            this.contentText = mensaje
            showAndWait()
        }
    }
    }
