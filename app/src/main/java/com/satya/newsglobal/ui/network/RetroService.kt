package com.satya.newsglobal.ui.network

import com.satya.newsglobal.ui.models.NewsServiceModel
import retrofit2.http.GET
import retrofit2.http.Query

interface RetroService {

    @GET("news")
    suspend fun getNews(@Query("category") category: String): NewsServiceModel
}