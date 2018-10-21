package net.slipp.slippboard

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.github.galcyurio.fakeapi.data.Post
import com.github.galcyurio.slippboardapi.SlippBoardClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_list.*
import net.slipp.slippboard.misc.BoardItemAdapter

class ListActivity : AppCompatActivity() {
    private val adapter by lazy { BoardItemAdapter() }
    private val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        recycler.adapter = adapter
        fabAdd.setOnClickListener {
            val intent = Intent(this, WriteActivity::class.java)
            startActivityForResult(intent, WriteActivity.CODE)
        }

        findBoards()
    }

    override fun onDestroy() {
        disposable.clear()
        super.onDestroy()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode != Activity.RESULT_OK) return

        when (requestCode) {
            WriteActivity.CODE -> {
                val post = data?.getSerializableExtra("post") as? Post
                // TODO: board 로 바꾸기
            }
        }
    }

    fun findBoards() {
        SlippBoardClient.boards()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { showLoading() }
            .doFinally { hideLoading() }
            .subscribeBy(
                onSuccess = { adapter.add(it) },
                onError = {
                    showErrorToast()
                    Log.e(javaClass.name, "findBoards onError", it)
                }
            )
            .addTo(disposable)
    }

    fun showLoading() {
        loading.show()
    }

    fun hideLoading() {
        loading.hide()
    }

    fun showErrorToast() {
        Toast.makeText(this, "에러 발생", Toast.LENGTH_SHORT).show()
    }
}
