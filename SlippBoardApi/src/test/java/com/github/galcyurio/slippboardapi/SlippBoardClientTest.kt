package com.github.galcyurio.slippboardapi

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Test
import java.io.File
import java.net.HttpURLConnection

class SlippBoardClientTest {

    private lateinit var server: MockWebServer
    private lateinit var response: MockResponse

    @Before
    fun setUp() {
        server = MockWebServer()
        response = MockResponse()
        server.enqueue(response)

        SlippBoardClient.init(baseUrl = server.url(""))
    }

    @Test
    fun `boards() 호출`() {
        val json = File(javaClass.classLoader.getResource("board-posts.json").toURI()).readText()
        println(json)

        response.apply {
            setResponseCode(HttpURLConnection.HTTP_OK)
            setBody(json)
        }


        val boards = SlippBoardClient.boards().blockingGet()
        println(boards)
    }
}