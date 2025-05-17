package org.example.practicaenequipocristianvictoraitornico.view.acercaDe

import com.vaadin.open.Open
import javafx.fxml.FXML
import javafx.scene.control.Hyperlink
import org.lighthousegames.logging.logging

private val logger = logging()

class AcrecaDeController {
    @FXML
    private lateinit var gitHubRepository: Hyperlink

    @FXML
    fun initialize() {
        logger.debug { "Inicializando AcercaDeController" }
        gitHubRepository.setOnAction {
            val url = "https://github.com/Cristianortegaa/Practica-en-equipo-Cristian-Victor-Aitor-Nico/tree/main"
            logger.debug { "Abriendo enlace a github" }
            Open.open(url)
        }
    }
}