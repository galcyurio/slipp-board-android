package net.slipp.slippboard.misc

import com.github.galcyurio.slippboardapi.data.Board
import io.reactivex.subjects.BehaviorSubject

object RxBus {
    val lastCreatedBoard = BehaviorSubject.create<Board>()
}