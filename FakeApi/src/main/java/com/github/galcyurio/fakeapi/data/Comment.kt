package com.github.galcyurio.fakeapi.data

import com.github.galcyurio.fakeapi.NoArgsConstructor

@NoArgsConstructor
data class Comment(
    val email: String,
    val name: String,
    val body: String = "",

    val id: Long = 0,
    val postId: Long = 0
)
