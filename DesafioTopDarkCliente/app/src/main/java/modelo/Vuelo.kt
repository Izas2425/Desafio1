package modelo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Vuelo(
    @SerializedName("idmision")
    var idmision: Int? = null,

    @SerializedName("duracion")
    var duracion: Int? = null,

    @SerializedName("carga")
    var carga: Boolean? = null,

    @SerializedName("pasajeros")
    var pasajeros: Boolean? = null
):Serializable
