package org.example.practicaenequipocristianvictoraitornico.players.storage

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import org.example.practicaenequipocristianvictoraitornico.common.config.Config
import org.example.practicaenequipocristianvictoraitornico.players.exception.PersonasException
import org.example.practicaenequipocristianvictoraitornico.players.models.Jugadores
import org.example.practicaenequipocristianvictoraitornico.players.models.Persona

import org.lighthousegames.logging.logging
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import java.util.zip.ZipOutputStream
import kotlin.io.path.name

class PersonalStorageZip(
    private val config: Config,
    private val csv: PersonalStorageCsv,
    private val json: PersonalStorageJson,
    private val bin: PersonalStorageBin,
    private val xml: PersonalStorageXml,
) {
    private val tempDirName = "players"
    private val logger = logging()
    fun leerDelArchivo(file: File): Result<List<Persona>, PersonasException> {
        logger.info { "descomprimiendo archivos" }
        val tempDir = Files.createTempDirectory(tempDirName)
        try {
            ZipFile(file).use { zip ->
                zip.entries().asSequence().forEach { entry ->
                    zip.getInputStream(entry).use { input ->
                        Files.copy(
                            input,
                            Paths.get(tempDir.toString(), entry.name),
                            StandardCopyOption.REPLACE_EXISTING
                        )
                    }
                }
            }

            Files.walk(tempDir).forEach {
                if (!it.toString().endsWith(".csv") && Files.isRegularFile(it) || !it.toString()
                        .endsWith(".json") && Files.isRegularFile(it) ||
                    !it.toString().endsWith(".bin") && Files.isRegularFile(it) || !it.toString()
                        .endsWith(".xml") && Files.isRegularFile(it)
                ) {
                    Files.copy(
                        it,
                        Paths.get(config.configProperties.imgDir + "/", it.name),
                        StandardCopyOption.REPLACE_EXISTING
                    )
                }
            }

            val personas = Files.walk(tempDir)
                .filter { Files.isRegularFile(it) && it.toString().matches(Regex(".*\\.(csv|json|bin|xml)$")) }
                .map {
                    it.toFile()
                }.toList()
            return when (personas.first().extension) {
                "csv" -> csv.leerDelArchivo(personas.first())
                "json" -> json.leerDelArchivo(personas.first())
                "bin" -> bin.leerDelArchivo(personas.first())
                "xml" -> xml.leerDelArchivo(personas.first())

                else -> {
                    return Err(PersonasException.PersonasStorageException("tipo invalido"))
                }
            }

        } catch (ex: Exception) {
            return Err(PersonasException.PersonasStorageException("tipo invalido"))
        }
    }


    fun escribirAUnArchivo(file: File, persona: List<Persona>, tipe: Tipo): Result<String, PersonasException> {
        logger.debug { "exportando a zip con datos en formato: $tipe " }
        val tempDir = Files.createTempDirectory(tempDirName)
        try {
            persona.forEach {
                if (it is Jugadores) {
                    val newfile = File(config.configProperties.imgDir + "/" + it.imagen)
                    if (newfile.exists()) {
                        Files.copy(
                            newfile.toPath(),
                            Paths.get("$tempDir/", file.name),
                            StandardCopyOption.REPLACE_EXISTING
                        )
                    }
                }
            }

            val datafile = when (tipe) {
                Tipo.CSV -> csv.escribirAUnArchivo(File("$tempDirName/data.csv"), persona)
                Tipo.JSON -> json.escribirAUnArchivo(File("$tempDirName/json.json"), persona)
                Tipo.XML -> xml.escribirAUnArchivo(File("$tempDirName/xml.json"), persona)
                Tipo.BIN -> bin.escribirAUnArchivo(File("$tempDirName/bin.csv"), persona)
            }
            if (datafile.isOk) {
                val archivos = Files.walk(tempDir).filter { Files.isRegularFile(it) }.toList()
                ZipOutputStream(Files.newOutputStream(file.toPath())).use { zip ->
                    archivos.forEach { archivos ->
                        val zipEntry = ZipEntry(tempDir.relativize(archivos).toString())
                        zip.putNextEntry(zipEntry)
                        Files.copy(archivos, zip)
                        zip.closeEntry()
                    }
                }
                tempDir.toFile().deleteRecursively()
                return Ok(file.absolutePath)
            } else return Err(PersonasException.PersonasStorageException(datafile.error.toString()))
        } catch (e: Exception) {
            logger.debug { "fallo al importar" }
            return Err(PersonasException.PersonasStorageException(e.message.toString()))
        }
    }
}
enum class Tipo {
    CSV, JSON, XML, BIN
}
