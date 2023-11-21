package com.example.thoughtctl

import retrofit2.http.GET
import retrofit2.http.Query

interface ImgurService {
    @GET("gallery/search/top/week")
    suspend fun getTopImages(
        @Query("q") query: String
    ): List<ImgurImage>
}