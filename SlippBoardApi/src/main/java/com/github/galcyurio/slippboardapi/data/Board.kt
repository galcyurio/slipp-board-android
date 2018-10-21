package com.github.galcyurio.slippboardapi.data

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.github.galcyurio.slippboardapi.BoardDeserializer
import com.github.galcyurio.slippboardapi.BoardSerializer
import java.util.*

@JsonDeserialize(using = BoardDeserializer::class)
@JsonSerialize(using = BoardSerializer::class)
data class Board(
    val title: String = "",
    val content: String = "",

    val id: Long? = null,
    val username: String? = null,
    val createdDateTime: Date? = null,
    val updatedDateTime: Date? = null
)