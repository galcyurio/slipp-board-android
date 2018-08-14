package com.github.galcyurio.fakeapi

import com.github.galcyurio.fakeapi.data.Post
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory
import java.io.File
import java.net.HttpURLConnection

class FakeApiTest {

    private lateinit var mockResponse: MockResponse
    private lateinit var server: MockWebServer
    private lateinit var fakeApi: FakeApi

    @Before fun setUp() {
        server = MockWebServer()
        server.start()

        fakeApi = Retrofit.Builder()
            .baseUrl(server.url(""))
            .addConverterFactory(JacksonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(FakeApi::class.java)
    }

    @After fun tearDown() {
        server.shutdown()
    }

    @Test fun `findPosts 호출시 Post 리스트 반환`() {
        mockResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(readJson("get-posts.json"))
        server.enqueue(mockResponse)

        fakeApi.findPosts()
            .test()
            .assertNoErrors()
            .assertComplete()
            .values().flatten().forEach { println(it) }
    }

    @Test fun `findPostById 호출시 Post 반환`() {
        mockResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(readJson("get-posts-1.json"))
        server.enqueue(mockResponse)

        val id = 1L
        fakeApi.findPostById(id)
            .test()
            .assertValueCount(1)
            .assertNoErrors()
            .assertComplete()
            .assertValue { it.id == id }
            .values().forEach { println(it) }
    }

    @Test fun `findPostComments 호출시 Comments 반환`() {
        mockResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(readJson("get-posts-1-comments.json"))
        server.enqueue(mockResponse)

        val id = 1L
        fakeApi.findPostComments(id)
            .test()
            .assertNoErrors()
            .assertComplete()
            .assertValue { comments -> comments.all { it.postId == id } }
            .values().flatten().forEach { println(it) }
    }


    @Test fun `findPostsByUserId 호출시 UserId에 맞는 Post 반환`() {
        mockResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(readJson("get-posts-userid-1.json"))
        server.enqueue(mockResponse)

        val id = 1L
        fakeApi.findPostsByUserId(id)
            .test()
            .assertNoErrors()
            .assertComplete()
            .assertValue { posts -> posts.all { it.userId == id } }
            .values().flatten().forEach { println(it) }
    }

    @Test fun `savePost 호출시 Complete 이벤트 발생`() {
        mockResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_CREATED)
        server.enqueue(mockResponse)

        fakeApi.savePost(Post("dummy"))
            .test()
            .assertNoErrors()
            .assertComplete()
            .values().forEach { println(it) }
    }

    @Test fun `updatePost 호출시 Complete 이벤트 발생`() {
        mockResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
        server.enqueue(mockResponse)

        fakeApi.updatePost(1L, Post("dummy"))
            .test()
            .assertNoErrors()
            .assertComplete()
            .values().forEach { println(it) }
    }

    @Test fun `deletePost 호출시 Complete 이벤트 발생`() {
        mockResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
        server.enqueue(mockResponse)

        fakeApi.deletePost(1L)
            .test()
            .assertNoErrors()
            .assertComplete()
            .values().forEach { println(it) }
    }

    /* ================= 실패시 =================== */

    @Test fun `findPosts 호출 실패시 Error 이벤트 발생`() {
        mockResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_NOT_FOUND)
        server.enqueue(mockResponse)

        fakeApi.findPosts()
            .test()
            .assertError(HttpException::class.java)
            .assertNotComplete()
    }

    // 생략....

    private fun readJson(fileName: String) =
        File(javaClass.classLoader.getResource(fileName).path).readText()
}