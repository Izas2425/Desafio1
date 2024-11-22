package modelo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Combate(
    @SerializedName("idmision")
    var idmision: Int? = null,

    @SerializedName("cazas")
    var cazas: Int? = null
): Serializable
