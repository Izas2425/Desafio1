package modelo

import kotlinx.serialization.Serializable


@Serializable
data class Vuelo(val idmision: Int, val duracion: Int, val carga: Boolean, val pasajeros: Boolean)
