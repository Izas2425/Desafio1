package api

import modelo.Usuario
import modelo.UsuarioLogIn
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UsuarioAPI {

    @GET("listadoUsuarios")
    suspend fun getUsuarios(): Response<MutableList<Usuario>>

    @GET("listadoPilotos")
    suspend fun getPilotos(): Response<MutableList<Usuario>>

    @GET("listadoUsuarios/{id}")
    suspend fun getUsuario(@Path("id") id: Int): Response<Usuario>

    @POST("login")
    suspend fun  login(@Body userData: UsuarioLogIn): Response<Usuario>

    @POST("registrar")
    suspend fun addUsuario(@Body userData: Usuario): Response<Boolean>

    @DELETE("borrar/{id}")
    suspend fun deleteUsuario(@Path("id") id: Int): Response<Boolean>

    @PUT("modificar/{id}")
    suspend fun updateUsuario(@Path("id") id: Int, @Body userData: Usuario): Response<Boolean>
}