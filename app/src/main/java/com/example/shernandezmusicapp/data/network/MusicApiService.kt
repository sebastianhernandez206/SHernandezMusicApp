package com.example.shernandezmusicapp.data.network

import com.example.shernandezmusicapp.data.model.Album
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface MusicApiService {
    @GET("api/albums")
    suspend fun getAlbums(): List<Album>

    @GET("api/albums/{id}")
    suspend fun getAlbum(@Path("id") id: String): Album

    companion object {
        private const val BASE_URL = "https://musicapi.pjasoft.com/"

        fun create(): MusicApiService {
            val contentType = "application/json".toMediaType()
            val json = Json { ignoreUnknownKeys = true }
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(json.asConverterFactory(contentType))
                .build()
                .create(MusicApiService::class.java)
        }
    }
}
