package modelo

import kotlinx.serialization.Serializable

@Serializable
data class Mision(val idmision: Int, val nombre: String, val experiencia: Int, val matriculanave: String, val descripcion: String )
