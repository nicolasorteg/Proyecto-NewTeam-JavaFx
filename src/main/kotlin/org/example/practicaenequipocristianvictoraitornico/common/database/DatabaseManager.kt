package org.example.practicaenequipocristianvictoraitornico.common.database
import org.example.practicaenequipocristianvictoraitornico.common.config.Config
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.kotlin.KotlinPlugin
import org.jdbi.v3.sqlobject.SqlObjectPlugin
import org.lighthousegames.logging.logging


class DatabaseManager {
    private val logger= logging()
    val jdbi: Jdbi by lazy {
        logger.debug { "inicializando jdbi" }
        Jdbi.create(Config.configProperties.url)
    }
    init {
        jdbi.installPlugin(KotlinPlugin())
        jdbi.installPlugin(SqlObjectPlugin())
        if (Config.configProperties.initTables){
            ejecutarScriptSql("config/tables.sql")
        }
        if (Config.configProperties.initData){
            ejecutarScriptSql("config/data.sql")
        }
    }

    private fun ejecutarScriptSql(file: String) {
        val scriptString= ClassLoader.getSystemResourceAsStream(file)?.bufferedReader()!!.readText()
        jdbi.useHandle<Exception> {
            it.createScript(scriptString).execute()
        }
    }
}
fun provideDatabaseManager(): Jdbi {
    val logger = logging()
    logger.debug { "obteniendo database manager" }
    val databaseManager = DatabaseManager()
    return databaseManager.jdbi
}


