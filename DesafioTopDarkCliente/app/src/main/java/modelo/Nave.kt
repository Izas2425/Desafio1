package modelo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Nave(
    @SerializedName("matricula")
    var matricula: String? = null,

    @SerializedName("foto")
    var foto:String? = null,

    @SerializedName("tipo")
    var tipo:String? = null,

    @SerializedName("carga")
    var carga:Boolean = false,

    @SerializedName("pasajeros")
    var pasajeros:Boolean = false,

): Serializable
