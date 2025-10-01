package robb.stark.proyectomobil.proyectomobil.API.repository

import ApiInterface
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetroFitHelper {
    private const val BASE_URL = "https://exercisedb.p.rapidapi.com/"
    private const val API_KEY = "32531b7eecmshf3b3b111e514e39p11a8f9jsn92958ede4a60"  // 🔑 reemplaza con tu clave real

    fun getRetrofitService(): ApiInterface {
        val client = OkHttpClient.Builder().addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("X-RapidAPI-Key", API_KEY)
                .addHeader("X-RapidAPI-Host", "exercisedb.p.rapidapi.com")
                .build()
            chain.proceed(request)
        }.build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)
    }
}
