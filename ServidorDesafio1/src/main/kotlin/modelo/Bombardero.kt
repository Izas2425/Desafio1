package modelo

import kotlinx.serialization.Serializable

@Serializable
data class Bombardero(val idmision: Int, val objetivos: Int, val carga: Boolean, val pasajeros: Boolean)
