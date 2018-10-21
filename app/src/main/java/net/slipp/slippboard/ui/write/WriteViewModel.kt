package net.slipp.slippboard.ui.write

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.github.galcyurio.slippboardapi.SlippBoardClient
import com.github.galcyurio.slippboardapi.data.Board
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class WriteViewModel : ViewModel() {

    private val disposable = CompositeDisposable()
    val onCreateComplete = MutableLiveData<Board>()
    val onError = MutableLiveData<Throwable>()

    val title = MutableLiveData<String>()
    val content = MutableLiveData<String>()

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

    fun write() {
        val board = Board(
            title = title.value ?: "",
            content = content.value ?: ""
        )

        // TODO: 로딩 UI 처리하기
        SlippBoardClient.create(board)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onComplete = { onCreateComplete.postValue(board) },
                onError = { onError.postValue(it) }
            )
            .addTo(disposable)
    }
}