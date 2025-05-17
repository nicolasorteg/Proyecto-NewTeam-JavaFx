package org.example.practicaenequipocristianvictoraitornico.players.repository

import org.example.practicaenequipocristianvictoraitornico.players.dao.PersonaDao
import org.example.practicaenequipocristianvictoraitornico.players.mappers.PersonaMapper
import org.example.practicaenequipocristianvictoraitornico.players.models.Entrenadores
import org.example.practicaenequipocristianvictoraitornico.players.models.Jugadores
import org.example.practicaenequipocristianvictoraitornico.players.models.Persona
import org.lighthousegames.logging.logging

class PersonasRepositoryImplementation(
    private val dao: PersonaDao,
    private val mapper: PersonaMapper,
):PersonalRepository{
    private val logger = logging()

    /**
     * @return devuelve la lista de personas
     */
    override fun getAll(): List<Persona> {
        logger.debug { "Getting all personas" }
        return dao.getAll().map { mapper.toDatabaseModel(it) }
    }

    /**
     * busca un usuario por id
     * @param id parametro que referencia al objeto
     * @return persona si se encuentra, nulo si no encuentra nada
     */
    override fun getById(id: Long): Persona? {
        logger.debug { "Getting persona by id $id" }
        return dao.getById(id.toInt())?.let { mapper.toDatabaseModel(it) }
    }

    /**
     * actualiza una persona en base a los datos y una id
     * @param objeto nuevo objeto a guardar
     * @param id parametro que referencia la objeto
     * @return persona si se encuentra, nulo si no se encuentra
     */
    override fun update(objeto: Persona, id: Long): Persona? {
        logger.debug { "Updating persona by id $id" }
    val updated = dao.update(mapper.toEntity(objeto),id.toInt())
        return if (updated==1) personaCopy(objeto,id) else null
    }

    /**
     * elimina en base a una id
     * @param id
     * @return devuelve persona si lo encuentra y elimina o nulo si no lo encuentra
     */
    override fun delete(id: Long): Persona? {
        logger.debug { "Deleting persona by id $id" }
        dao.getById(id.toInt())?.let {
            if (dao.deleteById(id.toInt())==1) return mapper.toDatabaseModel(it)
            else null
        }
        return null
    }

    /**
     * guarda una persona
     * @param objeto persona a guardar
     * @return persona guardada
     */
    override fun save(objeto: Persona): Persona {
        val id= dao.save(mapper.toEntity(objeto))
        return personaCopy(objeto,id.toLong())
    }

    /**
     * aplica un id a una persona
     * @param persona a copiar
     * @param id a aplicar
     */
    private fun personaCopy(persona: Persona,id: Long): Persona {
        return if(persona is Jugadores){
            jugadorCopy(persona, id)
        } else {
            entrenadorCopy(persona as Entrenadores,id)
        }
    }

    /**
     * aplica una id a un entrenador
     * @param persona entrenador a copiar
     * @param id id a aplicar
     * @return devuelve el entrenador
     */
    private fun entrenadorCopy(persona: Entrenadores, id: Long): Entrenadores {
        logger.debug { "copiando entrenador" }
        val entrenador = Entrenadores(
            id,
            persona.nombre,
            persona.apellidos,
            persona.fechaNacimiento,
            persona.fechaIncorporacion,
            persona.salario,
            persona.pais,
            persona.especialidad
        )
        return entrenador
    }

    /**
     * aplica una id a un jugador
     * @param persona jugador a copiar
     * @param id id a aplicar
     * @return devuelve el jugador
     */
    private fun jugadorCopy(persona: Jugadores, id: Long): Jugadores {
        logger.debug { "copiando jugador" }
        val jugador = Jugadores(
            id,
            persona.nombre,
            persona.apellidos,
            persona.fechaNacimiento,
            persona.fechaIncorporacion,
            persona.salario,
            persona.pais,
            persona.posicion,
            persona.dorsal,
            persona.altura,
            persona.peso,
            persona.goles,
            persona.partidosJugados,
            persona.imagen
        )
        return jugador
    }
}