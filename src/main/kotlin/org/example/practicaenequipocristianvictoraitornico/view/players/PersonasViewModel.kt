package org.example.practicaenequipocristianvictoraitornico.view.players

import com.github.michaelbull.result.Result
import com.github.michaelbull.result.andThen
import com.github.michaelbull.result.onSuccess
import javafx.beans.property.ListProperty
import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleObjectProperty
import org.example.practicaenequipocristianvictoraitornico.players.models.Jugadores
import org.example.practicaenequipocristianvictoraitornico.players.models.Persona
import org.example.practicaenequipocristianvictoraitornico.players.services.PersonaServiceImpl
import javafx.scene.image.Image
import org.example.practicaenequipocristianvictoraitornico.common.locale.round
import org.example.practicaenequipocristianvictoraitornico.common.locale.toLocalNumber
import org.example.practicaenequipocristianvictoraitornico.players.exception.PersonasException
import org.example.practicaenequipocristianvictoraitornico.players.models.Entrenadores
import org.example.practicaenequipocristianvictoraitornico.players.utils.toTipo
import org.example.practicaenequipocristianvictoraitornico.view.routes.RoutesManager
import org.example.practicaenequipocristianvictoraitornico.view.routes.logger
import java.io.File

class PersonasViewModel(
    private val service: PersonaServiceImpl,
) {
    fun savePersonas(file: File):Result<String,PersonasException> {
        val text= file.name.split(".")
        val archivo= File(text[0]+".zip")
        return service.exportarDatosDesdeFichero(archivo.toPath(),text[1].toTipo())
    }
    fun LoadPersonas(file: File):Result<List<Persona>, PersonasException> {
        return service.importarDatosDesdeFichero(file.toPath()).onSuccess {
            it.forEach { persona -> service.delete(persona.id).andThen { service.save(persona) }.onSuccess { loadAllAlumnos() } }
        }
    }

    private fun loadAllAlumnos() {
        service.getAll().onSuccess {
            logger.debug { "cargando alumnos del repositorio" }
            state.value= state.value.copy(lista = it.toList())
            updateActualState()
        }
    }

    private fun updateActualState() {
        logger.debug { "cargando estado" }
        var goles:Double=0.0
        var personas:Double=0.0
        for (persona in state.value.lista) {
            if (persona is Jugadores) {
                goles+=persona.goles
                personas++
            }
        }
        val avgGoles= (goles / personas).round(2).toLocalNumber()
        val numPersonas=state.value.lista.size
        state.value = state.value.copy(
            numeroPersonas = numPersonas.toString(),
            mediaGoles = avgGoles
        )
    }


    val state: SimpleObjectProperty<PersonasState> = SimpleObjectProperty(PersonasState())
    data class PersonasState(

        val jugadores: List<Jugadores> = emptyList(),
        val entrenadores: List<Entrenadores> = emptyList(),
        val mediaGoles: String= "0.0",
        val numeroPersonas: String = "0",
        val typeCentral: List<Jugadores> = emptyList(),
        val typeDelantero: List<Jugadores> = emptyList(),
        val typePortero: List<Jugadores> = emptyList(),
        val lista: List<Persona> = emptyList(),
    )

    data class PersonaState(
        val id: String = "",
        val nombre: String = "",
        val apellido: String = "",
        val fechaNacimiento: String = "",
        val fechaIncorporacion: String = "",
        val salario: String = "",
        val pais: String = "",
        val posicion: String = "",
        val expecialidad: String = "",
        val altura: String = "",
        val peso: String = "",
        val goles: String = "",
        val partidos: String = "",
        val imagen: Image= Image(RoutesManager.getResourceAsStream("benji.jpeg")),
        val fileImage: File?= null,
        val oldFileImage: File? = null
    )
}
/*
id:Long,
    nombre:String,
    apellidos:String,
    fechaNacimiento: LocalDate,
    fechaIncorporacion: LocalDate,
    salario:Double,
    pais:String,
    val posicion:Posicion,
    val dorsal:Int,
    val altura:Double,
    val peso:Double,
    val goles:Int,
    val partidosJugados:Int,
    val imagen: String
 */