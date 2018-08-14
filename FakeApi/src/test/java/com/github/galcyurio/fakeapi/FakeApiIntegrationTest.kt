package com.github.galcyurio.fakeapi

import com.github.galcyurio.fakeapi.data.Post
import org.junit.Test

class FakeApiIntegrationTest {

    private val fakeApi: FakeApi = fakeApi()

    @Test fun `findPosts 호출시 Post 리스트 반환`() {
        fakeApi.findPosts()
            .test()
            .assertNoErrors()
            .assertComplete()
            .values().flatten().forEach { println(it) }
    }

    @Test fun `findPostById 호출시 Post 반환`() {
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
        val id = 1L
        fakeApi.findPostComments(id)
            .test()
            .assertNoErrors()
            .assertComplete()
            .assertValue { comments -> comments.all { it.postId == id } }
            .values().flatten().forEach { println(it) }
    }


    @Test fun `findPostsByUserId 호출시 UserId에 맞는 Post 반환`() {
        val id = 1L
        fakeApi.findPostsByUserId(id)
            .test()
            .assertNoErrors()
            .assertComplete()
            .assertValue { posts -> posts.all { it.userId == id } }
            .values().flatten().forEach { println(it) }
    }

    @Test fun `savePost 호출시 Complete 이벤트 발생`() {
        fakeApi.savePost(Post("dummy"))
            .test()
            .assertNoErrors()
            .assertComplete()
            .values().forEach { println(it) }
    }

    @Test fun `updatePost 호출시 Complete 이벤트 발생`() {
        fakeApi.updatePost(1L, Post("dummy"))
            .test()
            .assertNoErrors()
            .assertComplete()
            .values().forEach { println(it) }
    }

    @Test fun `deletePost 호출시 Complete 이벤트 발생`() {
        fakeApi.deletePost(1L)
            .test()
            .assertNoErrors()
            .assertComplete()
            .values().forEach { println(it) }
    }
}