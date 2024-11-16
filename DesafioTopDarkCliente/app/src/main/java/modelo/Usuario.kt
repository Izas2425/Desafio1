package modelo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Usuario(
    @SerializedName("id")
    var id: Int? = null,

    @SerializedName("nombre")
    var nombre:String? = null,

    @SerializedName("password")
    var password:String? = null,

    @SerializedName("role")
    var role:String? = null,

    @SerializedName("edad")
    var edad:Int? = null,

    @SerializedName("experiencia")
    var experiencia:Int? = null,

    @SerializedName("nivel")
    var nivel:String? = null,

    @SerializedName("activado")
    var activado:Int? = null,

    @SerializedName("foto")
    var foto:String? = null
): Serializable
