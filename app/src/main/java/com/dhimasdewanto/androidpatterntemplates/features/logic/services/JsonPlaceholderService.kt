package com.dhimasdewanto.androidpatterntemplates.features.logic.services

import com.dhimasdewanto.androidpatterntemplates.core.API_ADDRESS
import com.dhimasdewanto.androidpatterntemplates.features.logic.models.UserModel
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface JsonPlaceholderService {
    @GET("users")
    suspend fun getListUsers(): List<UserModel>

    companion object {
        operator fun invoke(): JsonPlaceholderService {
            val httpClient = OkHttpClient.Builder()
                .build()

            return Retrofit.Builder()
                .client(httpClient)
                .baseUrl(API_ADDRESS)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(JsonPlaceholderService::class.java)
        }
    }
}