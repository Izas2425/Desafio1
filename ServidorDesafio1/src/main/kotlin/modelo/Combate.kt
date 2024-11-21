package modelo

import kotlinx.serialization.Serializable


@Serializable
data class Combate(val idmision: Int, val cazas: Int)
