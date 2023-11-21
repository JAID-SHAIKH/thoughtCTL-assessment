package com.example.thoughtctl

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ImgurRepository {

    private val imgurService: ImgurService by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.imgur.com/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ImgurService::class.java)
    }

    suspend fun getTopImages(query: String): List<ImgurImage> {
        return imgurService.getTopImages(query)
    }
}
