package modelo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Nave(
    @SerializedName("matricula")
    val matricula: String? = null,

    @SerializedName("foto")
    val foto:String? = null,

    @SerializedName("tipo")
    val tipo:String? = null,

    @SerializedName("carga")
    val carga:Boolean? = null,

    @SerializedName("pasajeros")
    val pasajeros:Boolean? = null,

): Serializable
