package org.example.practicaenequipocristianvictoraitornico.players.services

import com.github.michaelbull.result.Result
import org.example.practicaenequipocristianvictoraitornico.common.service.Service
import org.example.practicaenequipocristianvictoraitornico.players.exception.PersonasException
import org.example.practicaenequipocristianvictoraitornico.players.models.Persona
import org.example.practicaenequipocristianvictoraitornico.players.storage.Tipo
import java.nio.file.Path

interface PersonaService: Service<Persona, PersonasException, Long> {
    fun importarDatosDesdeFichero(fichero: Path): Result<List<Persona>, PersonasException>
    fun exportarDatosDesdeFichero(fichero: Path, tipo: Tipo): Result<String, PersonasException>
}