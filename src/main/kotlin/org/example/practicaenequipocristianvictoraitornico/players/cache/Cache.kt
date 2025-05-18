package org.example.practicaenequipocristianvictoraitornico.players.cache

import com.github.benmanes.caffeine.cache.Cache
import com.github.benmanes.caffeine.cache.Caffeine
import org.example.practicaenequipocristianvictoraitornico.common.config.Config
import org.example.practicaenequipocristianvictoraitornico.players.models.Persona
import org.lighthousegames.logging.logging


fun darPersonasCache(
):Cache<Long, Persona> {
    val size=Config.configProperties.capacity
    val logger= logging()
    logger.debug { "creando cache" }
    return Caffeine.newBuilder().maximumSize(size.toLong()).build()
}