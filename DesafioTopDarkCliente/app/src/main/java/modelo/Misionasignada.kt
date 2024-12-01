package modelo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Misionasignada(
    @SerializedName("id")
    var id: Int? = null,

    @SerializedName("idusuario")
    var idusuario: Int? = null,

    @SerializedName("idmision")
    var idmision: Int? = null,

    @SerializedName("estado")
    var estado: String? = null
):Serializable
