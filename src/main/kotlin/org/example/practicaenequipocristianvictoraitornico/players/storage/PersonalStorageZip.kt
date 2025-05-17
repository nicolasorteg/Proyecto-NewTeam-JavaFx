package org.example.practicaenequipocristianvictoraitornico.players.storage

import com.github.michaelbull.result.Result
import org.example.practicaenequipocristianvictoraitornico.players.exception.PersonasException
import org.example.practicaenequipocristianvictoraitornico.players.models.Persona
import java.io.File

class PersonalStorageZip {
    fun leerDelArchivo(file: File): Result<List<Persona>, PersonasException> {
       TODO()
    }

    fun escribirAUnArchivo(file: File, persona: List<Persona>,tipe:Tipo): Result<String, PersonasException> {
        TODO("Not yet implemented")
    }
}

enum class Tipo {
    CSV, JSON, XML, BIN
}
