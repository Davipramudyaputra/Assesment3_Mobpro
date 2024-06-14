package org.davipramudya.ass3.Network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.davipramudya.ass3.model.OpStatus
import org.davipramudya.ass3.model.Film
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

private const val BASE_URL = "https://api.davipramudya.my.id/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface FilmApiService {
    @GET("json.php")
    suspend fun getFilm(
        @Query("auth") userId: String
    ): List<Film>

    @Multipart
    @POST("json.php")
    suspend fun postFilm(
        @Part("auth") userId: String,
        @Part("judul") judul: RequestBody,
        @Part("sutradara") sutradara: RequestBody,
        @Part image: MultipartBody.Part
    ): OpStatus

    @DELETE("json.php")
    suspend fun deleteFilm(
        @Query("auth") userId: String,
        @Query("id") id: String
    ): OpStatus
}

object FilmApi {
    val service: FilmApiService by lazy {
        retrofit.create(FilmApiService::class.java)
    }
    fun getFilmUrl(image: String): String {
        return "$BASE_URL$image"
    }
}

enum class ApiStatus { LOADING, SUCCESS, FAILED }