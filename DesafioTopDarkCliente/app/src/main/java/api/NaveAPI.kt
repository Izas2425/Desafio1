package api

import modelo.Nave
import modelo.Usuario
import modelo.UsuarioLogIn
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface NaveAPI {
    @GET("listadoNaves")
    suspend fun getNaves(): Response<MutableList<Nave>>

    @GET("listadoNaves/{matricula}")
    suspend fun getNave(@Path("matricula") matricula: String): Response<Nave>

    @POST("registrarNave")
    suspend fun addNave(@Body userData: Nave): Response<Boolean>

    @DELETE("borrarNave/{Matricula}")
    suspend fun deleteNave(@Path("matricula") matricula: String): Response<Boolean>

    @PUT("modificarNave/{matricula}")
    suspend fun updateNave(@Path("matricula") matricula: String, @Body userData: Nave): Response<Boolean>
}