package com.github.galcyurio.slippboardapi

import com.github.galcyurio.slippboardapi.data.Board
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
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

        SlippBoardClient.init(baseUrl = server.url(""))
    }

    @After
    fun tearDown() {
        server.close()
    }

    @Test
    fun `boards() 호출`() {
        server.enqueue(response.apply {
            setResponseCode(HttpURLConnection.HTTP_OK)
            setBody(readJson("board-posts.json"))
        })

        SlippBoardClient.boards()
            .test()
            .assertNoErrors()
            .assertComplete()
    }

    @Test
    fun `update() 호출`() {
        server.enqueue(response.apply {
            setResponseCode(HttpURLConnection.HTTP_OK)
        })

        SlippBoardClient.update(Board())
            .test()
            .assertNoErrors()
            .assertComplete()
    }

    @Test
    fun `create() 호출`() {
        server.enqueue(response.apply {
            setResponseCode(HttpURLConnection.HTTP_OK)
        })

        SlippBoardClient.create(Board())
            .test()
            .assertNoErrors()
            .assertComplete()
    }

    @Test
    fun `delete() 호출`() {
        server.enqueue(response.apply {
            setResponseCode(HttpURLConnection.HTTP_OK)
        })

        SlippBoardClient.delete(1)
            .test()
            .assertNoErrors()
            .assertComplete()
    }

    @Test
    fun `popularBoard() 호출`() {
        server.enqueue(response.apply {
            setResponseCode(HttpURLConnection.HTTP_OK)
            setBody(readJson("board-popular.json"))
        })

        SlippBoardClient.popularBoards()
            .test()
            .assertNoErrors()
            .assertComplete()
    }

    fun readJson(path: String): String {
        return File(javaClass.classLoader.getResource(path).toURI()).readText()
    }
}