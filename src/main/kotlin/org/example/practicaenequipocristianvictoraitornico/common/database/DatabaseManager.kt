package org.example.practicaenequipocristianvictoraitornico.common.database
import org.example.practicaenequipocristianvictoraitornico.common.config.Config
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.kotlin.KotlinPlugin
import org.jdbi.v3.sqlobject.SqlObjectPlugin
import org.lighthousegames.logging.logging
import java.io.FileNotFoundException
import java.util.*


class DatabaseManager{
    private val logger= logging()
    val jdbi: Jdbi by lazy {
        logger.debug { "inicializando jdbi" }

        Jdbi.create("jdbc:h2:./jugadores")

    }
    init {
        jdbi.installPlugin(KotlinPlugin())
        jdbi.installPlugin(SqlObjectPlugin())

        ejecutarScriptSql("tables.sql")

        ejecutarScriptSql("data.sql")
    }

    private fun ejecutarScriptSql(file: String) {
        val scriptString= ClassLoader.getSystemResourceAsStream(file)?.bufferedReader()!!
        val script = scriptString.readText()
        jdbi.useHandle<Exception> {it->
            it.createScript(script).execute()
        }
    }
}
fun provideDatabaseManager(): Jdbi {
    val logger = logging()
    logger.debug { "Obteniendo config properties..." }

    val config = Config.configProperties

    logger.debug {
        "Config.url=${config.url}, initData=${config.initData}, initTables=${config.initTables}"
    }

    val databaseManager = DatabaseManager()
    return databaseManager.jdbi
}
