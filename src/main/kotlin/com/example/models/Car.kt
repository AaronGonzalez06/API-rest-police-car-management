package com.example.models
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class Car(
    val carRegistration: String,
    val model: String,
    var available: Boolean,
    var damaged: Boolean,
    val polices: MutableList<Police>
    )


val Cars = mutableListOf<Car>(
    Car("1234-ABC","Seat",false,false,mutableListOf<Police>(Police("67890123J", "Pedro", "Catalan",false,"Nothing"))),
    Car("6524-QWS","Audi",true,false,mutableListOf<Police>()),
    Car("6958-VFD","Mercedes",true,false,mutableListOf<Police>()),
    Car("3652-NHG","Citroen",false,true,mutableListOf<Police>()),
)
