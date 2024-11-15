package modelo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Usuario(
    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("nombre")
    val nombre:String? = null,

    @SerializedName("password")
    val password:String? = null,

    @SerializedName("role")
    val role:String? = null,

    @SerializedName("edad")
    val edad:Int? = null,

    @SerializedName("experiencia")
    val experiencia:Int? = null,

    @SerializedName("nivel")
    val nivel:String? = null,

    @SerializedName("activado")
    val activado:Int? = null,

    @SerializedName("foto")
    val foto:String? = null
): Serializable
