package org.example.practicaenequipocristianvictoraitornico.di

import com.github.benmanes.caffeine.cache.Cache
import org.example.practicaenequipocristianvictoraitornico.common.database.provideDatabaseManager
import org.example.practicaenequipocristianvictoraitornico.players.cache.darPersonasCache
import org.example.practicaenequipocristianvictoraitornico.players.dao.PersonaDao
import org.example.practicaenequipocristianvictoraitornico.players.dao.getPersonasDao
import org.example.practicaenequipocristianvictoraitornico.players.mappers.PersonaMapper
import org.example.practicaenequipocristianvictoraitornico.players.models.Persona
import org.example.practicaenequipocristianvictoraitornico.players.repository.PersonasRepositoryImplementation
import org.example.practicaenequipocristianvictoraitornico.players.services.PersonaService
import org.example.practicaenequipocristianvictoraitornico.players.storage.PersonalStorageZip
import org.example.practicaenequipocristianvictoraitornico.players.validator.PersonaValidation
import org.example.practicaenequipocristianvictoraitornico.players.services.PersonaServiceImpl
import org.example.practicaenequipocristianvictoraitornico.users.service.UsersService
import org.example.practicaenequipocristianvictoraitornico.users.service.UsersServiceImpl
import org.example.practicaenequipocristianvictoraitornico.users.mapper.UsersMapper
import org.example.practicaenequipocristianvictoraitornico.users.repository.UsersRepositoryImpl
import org.example.practicaenequipocristianvictoraitornico.users.dao.provideUsersDao
import org.example.practicaenequipocristianvictoraitornico.users.dao.UsersDao
import org.example.practicaenequipocristianvictoraitornico.users.repository.UsersRepository
import org.example.practicaenequipocristianvictoraitornico.view.controller.LoginViewModel
import org.jdbi.v3.core.Jdbi
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.koin.core.module.dsl.bind


/**
 * Koin module for the application.
 */
val appModule = module {

    // Base: Jdbi
    singleOf(::provideDatabaseManager) {
        bind<Jdbi>()
    }

    // DAO: depende de Jdbi
    singleOf(::provideUsersDao) {
        bind<UsersDao>()
    }

    // Repository: depende de DAO
    singleOf(::UsersRepositoryImpl) {
        bind<UsersRepositoryImpl>()
    }

    // Service: depende de Repository
    singleOf(::UsersServiceImpl) {
        bind<UsersServiceImpl>()
    }

    // Otros servicios y utilidades (ejemplo)
    singleOf(::UsersMapper) {
        bind<UsersMapper>()
    }

    // Tu ViewModel que puede depender de UsersService o repositorio
    singleOf(::LoginViewModel) {
        bind<LoginViewModel>()
    }

    // Otros binds que tengas para Personas, cache, validación...
    singleOf(::PersonalStorageZip) {
        bind<PersonalStorageZip>()
    }
    singleOf(::PersonasRepositoryImplementation) {
        bind<PersonasRepositoryImplementation>()
    }
    singleOf(::darPersonasCache) {
        bind<Cache<Long, Persona>>()
    }
    singleOf(::PersonaValidation) {
        bind<PersonaValidation>()
    }
    singleOf(::getPersonasDao) {
        bind<PersonaDao>()
    }
    singleOf(::PersonaMapper) {
        bind<PersonaMapper>()
    }
    singleOf(::UsersRepositoryImpl) {
        bind<UsersRepository>() // ✅ Esto es lo que te falta
    }
}








