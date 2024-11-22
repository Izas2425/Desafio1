package modelo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Mision(
    @SerializedName("idmision")
    var idmision: Int? = null,

    @SerializedName("nombre")
    var nombre: String? = null,

    @SerializedName("experiencia")
    var experiencia: Int? = null,

    @SerializedName("matriculanave")
    var matriculanave:String? = null,

    @SerializedName("descripcion")
    var descripcion: String?= null,
): Serializable
