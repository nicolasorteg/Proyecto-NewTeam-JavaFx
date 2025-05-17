package org.example.practicaenequipocristianvictoraitornico.players.storage

import com.github.michaelbull.result.Result
import org.example.practicaenequipocristianvictoraitornico.players.exception.PersonasException
import org.example.practicaenequipocristianvictoraitornico.players.models.Persona
import java.io.File

interface PersonalStorage {
    fun leerDelArchivo (file: File): Result<List<Persona>, PersonasException>
    fun escribirAUnArchivo (file: File, persona: List<Persona>): Result<String, PersonasException>

}