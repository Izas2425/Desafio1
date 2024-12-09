package api

import modelo.Misionasignada
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface MisionasignadaAPI {

    @GET("listadoMisionesasignadas")
    suspend fun getMisionesasignadas(): Response<MutableList<Misionasignada>>

    @GET("listadoMisionesasignadas/{id}")
    suspend fun getMisionesasignadas(@Path("id") id: Int): Response<MutableList<Misionasignada>>

    @GET("listadoMisionesasignadasSuperadas/{id}")
    suspend fun  getMisionesasignadasSuperadas(@Path("id") id: Int): Response<MutableList<Misionasignada>>

    @GET("listadoMisionesasignadasNoSuperadas/{id}")
    suspend fun getMisionesasignadasNoSuperadas(@Path("id") id: Int):Response<MutableList<Misionasignada>>

    @POST ("registrarMisionasignada")
    suspend fun addMisionasignada(@Body userData: Misionasignada):Response<Boolean>

    @DELETE ("borrarMisionasignada")
    suspend fun deleteMisionasignada(@Path("id") id: Int): Response<Boolean>

    @PUT("/actualizarMisionasignada/{id}")
    suspend fun updateMisionasignada(@Path("id") id: Int, @Body userData: Misionasignada):Response<Boolean>
}