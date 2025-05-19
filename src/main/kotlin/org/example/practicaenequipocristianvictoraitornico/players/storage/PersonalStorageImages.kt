package org.example.practicaenequipocristianvictoraitornico.players.storage

import com.github.michaelbull.result.Result
import org.example.practicaenequipocristianvictoraitornico.players.exception.PersonasException
import java.io.File

interface PersonalStorageImages {
    fun saveImage(fileName: File): Result<File, PersonasException>
    fun loadImage(fileName: String): Result<File, PersonasException>
    fun deleteImage(fileName: String): Result<Unit, PersonasException>
    fun deleteAllImage(): Result<Int, PersonasException>
    fun updateImage(Imagen: String, newFileImage: File): Result<File, PersonasException>
}