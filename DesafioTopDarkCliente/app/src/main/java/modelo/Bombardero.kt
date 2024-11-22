package modelo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Bombardero(
    @SerializedName("idmision")
    var idmision: Int? = null,

    @SerializedName("objetivos")
    var objetivos: Int? = null,

    @SerializedName("carga")
    var carga: Boolean? = null,

    @SerializedName("pasajeros")
    var pasajeros: Boolean? = null
):Serializable
