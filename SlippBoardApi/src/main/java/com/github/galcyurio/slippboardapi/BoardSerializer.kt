package com.github.galcyurio.slippboardapi

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import com.github.galcyurio.slippboardapi.data.Board

class BoardSerializer : StdSerializer<Board>(Board::class.java) {
    override fun serialize(value: Board, gen: JsonGenerator, provider: SerializerProvider) {
        gen.writeStartObject()
        value.id?.let { gen.writeNumberField("id", it) }
        gen.writeStringField("title", value.title)
        gen.writeStringField("content", value.content)
        gen.writeEndObject()
    }
}