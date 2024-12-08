package api

import modelo.Misionasignada
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import parametros.Parametros
import java.util.concurrent.TimeUnit

object UserNetwork {
    val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(Parametros.url + ":" + Parametros.puerto)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UsuarioAPI::class.java)
    }

    val retrofitNave by lazy {
        Retrofit.Builder()
            .baseUrl(Parametros.url + ":" + Parametros.puerto)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NaveAPI::class.java)
    }

    val retrofitMision  by lazy {
        Retrofit.Builder()
            .baseUrl(Parametros.url + ":" + Parametros.puerto)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MisionAPI::class.java)
    }

    val retrofitMisionAsignada  by lazy {
        Retrofit.Builder()
            .baseUrl(Parametros.url + ":" + Parametros.puerto)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MisionasignadaAPI::class.java)
    }

}