package com.github.galcyurio.fakeapi

import com.github.galcyurio.fakeapi.data.Comment
import com.github.galcyurio.fakeapi.data.Post
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.*

interface FakeApi {

    @GET("posts")
    fun findPosts(): Single<List<Post>>

    @GET("posts/{id}")
    fun findPostById(@Path("id") id: Long): Single<Post>

    @GET("posts/{id}/comments")
    fun findPostComments(@Path("id") id: Long): Single<List<Comment>>

    @GET("posts")
    fun findPostsByUserId(@Query("userId") userId: Long): Single<List<Post>>

    @POST("posts")
    fun savePost(@Body post: Post): Completable

    @PUT("posts/{id}")
    fun updatePost(@Path("id") id: Long, @Body post: Post): Completable

    @DELETE("posts/{id}")
    fun deletePost(@Path("id") id: Long): Completable
}
