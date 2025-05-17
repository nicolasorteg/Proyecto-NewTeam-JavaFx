package org.example.practicaenequipocristianvictoraitornico.players.dto




class JugadorDto(
    id: Long,
    nombre: String,
    apellidos: String,
    fechaNacimiento: String,
    fechaIncorporacion: String,
    salario: Double,
    pais: String,
    val posicion: String,
    val dorsal: Int,
    val altura: Double,
    val peso: Double,
    val goles: Int,
    val partidosJugados: Int,
    val imagen: String
): PersonaDto(id, nombre, apellidos, fechaNacimiento, fechaIncorporacion, salario, pais)