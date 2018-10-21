package com.github.galcyurio.slippboardapi.data

import java.util.*

data class Board(
        val id: Long,
        val username: String,
        val title: String,
        val content: String,
        val createdDateTime: Date? = null,
        val updatedDateTime: Date? = null
)