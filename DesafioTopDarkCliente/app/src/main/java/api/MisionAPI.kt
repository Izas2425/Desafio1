package api

import modelo.Bombardero
import modelo.Combate
import modelo.Mision
import modelo.Vuelo
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface MisionAPI {
    @GET("listadoMisiones")
    suspend fun getMisiones(): Response<MutableList<Mision>>

    @GET("listadoMisiones/{idmision}")
    suspend fun getMision(@Path("idmision") idmision: Int): Response<Mision>

    // Devuelve el ultimo id de misiones
    @GET("ultimoId")
    suspend fun getUltimoId(): Response<Int>

    @GET("listadoVuelos")
    suspend fun getVuelos(): Response<MutableList<Vuelo>>

    @GET("listadoVuelos{idmision}")
    suspend fun getVuelo(@Path("idmision") idmision: Int): Response<Vuelo>

    @GET("listadoCombates")
    suspend fun getCombates(): Response<MutableList<Combate>>

    @GET("listadoCombates/{idmision")
    suspend fun getCombate(@Path("idmision") idmision: Int): Response<Combate>

    @GET("listadoBombarderos")
    suspend fun getBombarderos(): Response<MutableList<Bombardero>>

    @GET("listadoBombarderos/{idmision}")
    suspend fun getBombardero(@Path("idmision") idmision: Int): Response<Bombardero>

    @POST("registrarMision")
    suspend fun addMision(@Body userData: Mision): Response<Boolean>

    @POST("registrarVuelo")
    suspend fun addVuelo(@Body userData: Vuelo): Response<Boolean>

    @POST("registrarCombate")
    suspend fun addCombate(@Body userData: Combate): Response<Boolean>

    @POST("registrarBombardero")
    suspend fun addBombardero(@Body userData: Bombardero): Response<Boolean>

    @DELETE("borrarMision/{idmision}")
    suspend fun deleteMision(@Path("idmision") idmision: Int):Response<Boolean>

    @DELETE("borrarVuelo/{ídmision}")
    suspend fun deleteVuelo(@Path("idmision") idmision: Int):Response<Boolean>

    @DELETE("borrarCombate({idmision")
    suspend fun deleteCombate(@Path("idmision") idmision: Int): Response<Boolean>

    @DELETE("borrarBombardero{idmision}")
    suspend fun deleteBombardero(@Path("idmision") idmision: Int): Response<Boolean>
}