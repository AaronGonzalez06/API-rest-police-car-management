package com.example.models

import kotlinx.serialization.Serializable
import java.time.LocalDate
@Serializable
data class Police(
    val dni: String,
    val name: String,
    val surname: String,
    var available: Boolean,
    var range: String,
)

val polices = mutableListOf<Police>(
    Police("12345678A", "Aarón", "González",true,"Official"),
    Police("87654321E", "Pepe", "Torres",true,"Official"),
    Police("43210987H", "Jose", "Álvarez",true,"Sergeant"),
    Police("89012345I", "María", "Martín",true,"Sergeant"),
    Police("67890123J", "Pedro", "Catalan",false,"Nothing"),
    Police("98765432B", "Paco", "Triguero",true,"Nothing"),
)