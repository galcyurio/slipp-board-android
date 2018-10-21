package com.github.galcyurio.slippboardapi

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.github.galcyurio.slippboardapi.data.Board
import java.util.*

class BoardDeserializer : StdDeserializer<Board>(Board::class.java) {
    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): Board {
        val node = p.codec.readTree<JsonNode>(p)

        val createDtmNode: JsonNode? = node.get("createDtm")
        val updateDtmNode: JsonNode? = node.get("updatedDtm")

        return Board(
            id = node.get("id").longValue(),
            username = node.get("userId").textValue(),
            title = node.get("title").textValue(),
            content = node.get("content").textValue(),
            createdDateTime = createDtmNode?.longValue()?.let { Date(it) },
            updatedDateTime = updateDtmNode?.longValue()?.let { Date(it) }
        )
    }
}