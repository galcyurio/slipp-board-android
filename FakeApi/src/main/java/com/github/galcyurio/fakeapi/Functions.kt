package com.github.galcyurio.fakeapi

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory

const val BASE_URL = "https://jsonplaceholder.typicode.com/"

fun fakeApiRequest(): FakeApi {
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(JacksonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(FakeApi::class.java)
}