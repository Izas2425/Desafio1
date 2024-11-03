package modelo

import io.ktor.http.*
import kotlinx.serialization.Serializable
import java.awt.Image

@Serializable
data class Usuario(val id: Int, val nombre: String, val password: String, val role: String, val edad: Int, val experiencia: Int, val foto: String)
