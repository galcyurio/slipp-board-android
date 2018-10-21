package net.slipp.slippboard.ui.list

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.github.galcyurio.slippboardapi.SlippBoardClient
import com.github.galcyurio.slippboardapi.data.Board
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class ListViewModel : ViewModel() {

    private val disposable = CompositeDisposable()

    val boards = MutableLiveData<List<Board>>()
    val isLoading = MutableLiveData<Boolean>()
    val onError = MutableLiveData<Throwable>()

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

    fun findBoards() {
        SlippBoardClient.boards()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { isLoading.postValue(true) }
            .doFinally { isLoading.postValue(false) }
            .doOnSuccess { boards.postValue(it) }
            .subscribeBy(
                onError = {
                    onError.postValue(it)
                    Log.e(javaClass.name, "findBoards onError", it)
                }
            )
            .addTo(disposable)
    }
}