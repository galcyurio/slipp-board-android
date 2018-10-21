package com.github.galcyurio.slippboardapi

import com.github.galcyurio.slippboardapi.data.Board
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.*

interface SlippBoardApi {
    @GET("board/posts")
    fun boards(): Single<List<Board>>

    @POST("board/post")
    fun create(@Body board: Board): Completable

    @PUT("board/post")
    fun update(@Body board: Board): Completable

    @DELETE("board/post/{id}")
    fun delete(@Path("id") id: Long): Completable

    @GET("board/popular")
    fun popularBoards(): Single<List<Board>>
}