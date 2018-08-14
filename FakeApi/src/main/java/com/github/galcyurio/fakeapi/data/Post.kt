package com.github.galcyurio.fakeapi.data

data class Post(
    val title: String,
    val body: String = "",

    val id: Long? = 0,
    val userId: Long? = 0
)
