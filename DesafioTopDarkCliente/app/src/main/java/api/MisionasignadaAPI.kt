package api

import android.service.autofill.UserData
import modelo.Misionasignada
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface MisionasignadaAPI {

    @GET("listadoMisionesasignadas")
    suspend fun getMisionesasignadas(): Response<MutableList<Misionasignada>>

    @GET("listadoMisionesasignadas/{id}")
    suspend fun getMisionasignada(@Path("id") id: Int): Response<Misionasignada>

    @GET("listadoMisionesasignadasSuperadas")
    suspend fun  getMisionesasignadasSuperadas(): Response<MutableList<Misionasignada>>

    @GET("listadoMisionesasignadasNoSuperadas")
    suspend fun getMisionesasignadasNoSuperadas():Response<MutableList<Misionasignada>>

    @POST ("registrarMisionasignada")
    suspend fun addMisionasignada(@Body userData: UserData):Response<Boolean>

    @DELETE ("borrarMisionasignada")
    suspend fun deleteMisionasignada(@Path("id") id: Int): Response<Boolean>
}