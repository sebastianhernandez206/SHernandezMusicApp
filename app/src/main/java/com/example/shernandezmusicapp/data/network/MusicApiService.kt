package com.example.shernandezmusicapp.data.network

import com.example.shernandezmusicapp.data.model.Album
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MusicApiService::class.java)
        }
    }
}
