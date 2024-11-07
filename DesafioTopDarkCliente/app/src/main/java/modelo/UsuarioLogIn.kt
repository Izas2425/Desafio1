package modelo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class UsuarioLogIn(
    @SerializedName("nombre")
    val nombre:String? = null,

    @SerializedName("password")
    val password:String? = null

): Serializable