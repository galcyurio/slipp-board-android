package com.github.galcyurio.slippboardapi

import com.github.galcyurio.slippboardapi.data.Board
import io.reactivex.Completable
import io.reactivex.Single

object SlippBoardClient {

    internal lateinit var api: SlippBoardApi

    fun init(baseUrl: String = BASE_URL) {
        api = Injector.provideRetrofit()
            .newBuilder().baseUrl(baseUrl)
            .build()
            .create(SlippBoardApi::class.java)
    }

    fun boards(): Single<List<Board>> = api.boards()
    fun create(board: Board): Completable = api.create(board)
    fun update(board: Board): Completable = api.update(board)
    fun delete(id: Long): Completable = api.delete(id)
    fun popularBoards(): Single<List<Board>> = api.popularBoards()
}