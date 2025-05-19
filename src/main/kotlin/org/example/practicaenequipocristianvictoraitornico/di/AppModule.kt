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
import org.jdbi.v3.core.Jdbi
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.koin.core.module.dsl.bind
import org.example.practicaenequipocristianvictoraitornico.view.players.PersonasViewModel
import org.example.practicaenequipocristianvictoraitornico.view.controller.LoginViewModel


/**
 * Koin module for the application.
 */
val appModule = module {

    singleOf(::PersonalStorageZip) {
        bind<PersonalStorageZip>()
    }
    singleOf(::PersonasRepositoryImplementation){
        bind<PersonasRepositoryImplementation>()
    }
    singleOf(::provideDatabaseManager){
        bind<Jdbi>()
    }
    singleOf(::darPersonasCache){
        bind<Cache<Long, Persona>>()
    }
    singleOf(::PersonaValidation){
        bind<PersonaValidation>()
    }
    singleOf(::getPersonasDao){
        bind<PersonaDao>()
    }
    singleOf(::PersonaMapper){
        bind<PersonaMapper>()
    }
    singleOf(::PersonaServiceImpl){
        bind<PersonaService>()
    }
    singleOf(::UsersServiceImpl){
        bind<UsersService>()
    }
    singleOf(::UsersMapper){
        bind<UsersMapper>()
    }
    singleOf(::UsersRepositoryImpl){
        bind<UsersRepositoryImpl>()
    }
    singleOf(::provideUsersDao){
        bind<UsersDao>()
    }

    singleOf(::PersonasViewModel)
    singleOf(::LoginViewModel)
}