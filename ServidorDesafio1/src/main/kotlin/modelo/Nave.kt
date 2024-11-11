package modelo

import kotlinx.serialization.Serializable

@Serializable
data class Nave(val matricula: String, val foto: String, val tipo: String, val carga: Boolean, val pasajeros: Boolean)
