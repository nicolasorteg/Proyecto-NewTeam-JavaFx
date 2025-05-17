package org.example.practicaenequipocristianvictoraitornico.users.service

import org.example.practicaenequipocristianvictoraitornico.common.service.Service
import org.example.practicaenequipocristianvictoraitornico.users.exception.UsersException
import org.example.practicaenequipocristianvictoraitornico.users.models.User

interface UsersService: Service<User, UsersException, String> {
}