package com.github.galcyurio.fakeapi.data

import com.github.galcyurio.fakeapi.NoArgsConstructor

@NoArgsConstructor
data class Post(
    val title: String,
    val body: String = "",

    val id: Long? = 0,
    val userId: Long? = 0
)
