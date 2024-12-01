package modelo

import com.google.gson.annotations.SerializedName

data class MostrarMision(
    @SerializedName("idmision")
    var idmision: Int? = null,

    @SerializedName("nombre")
    var nombre: String? = null,

    @SerializedName("experiencia")
    var experiencia: Int? = null,

)
