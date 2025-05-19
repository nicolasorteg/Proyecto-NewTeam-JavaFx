package org.example.practicaenequipocristianvictoraitornico.players.utils

import org.example.practicaenequipocristianvictoraitornico.players.models.Especialidad
import org.example.practicaenequipocristianvictoraitornico.players.models.Posicion
import org.example.practicaenequipocristianvictoraitornico.players.storage.Tipo
import java.util.*

fun String.toEspecialidad(): Especialidad? {
    return if (this.uppercase(Locale.getDefault()) != "ENTRENADOR_PORTEROS" &&
        this.uppercase(Locale.getDefault()) != "ENTRENADOR_ASISTENTE" &&
        this.uppercase(Locale.getDefault()) != "ENTRENADOR_PRINCIPAL")
        null else Especialidad.valueOf(this.uppercase(Locale.getDefault()))
}
fun String.toPosicion(): Posicion? {
    return if (this.uppercase(Locale.getDefault()) != "DELANTERO" &&
        this.uppercase(Locale.getDefault()) != "CENTROCAMPISTA" && this.uppercase(Locale.getDefault()) != "PORTERO" &&
        this.uppercase(Locale.getDefault()) != "DEFENSA") null else Posicion.valueOf(this.uppercase(Locale.getDefault()))

}
fun String.toTipo(): Tipo {
    return when(this.uppercase(Locale.getDefault())){
        "CSV" -> Tipo.CSV
        "JSON"-> Tipo.JSON
        "XML" -> Tipo.XML
        else -> Tipo.BIN
    }
}