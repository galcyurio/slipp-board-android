package com.github.galcyurio.slippboardapi

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory

internal object Injector {
    fun provideObjectMapper(): ObjectMapper {
        return ObjectMapper()
                .registerKotlinModule()
    }

    fun provideRetrofit(
            objectMapper: ObjectMapper = provideObjectMapper(),
            okHttpClient: OkHttpClient = provideOkHttpClient()
    ): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .client(okHttpClient)
                .build()
    }

    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
                .build()
    }
}