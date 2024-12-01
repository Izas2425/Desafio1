package modelo

import kotlinx.serialization.Serializable

@Serializable
data class Misionasignada(val id: Int, val idusuario: Int, val idmision: Int, val estado: Int)
