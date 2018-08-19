package net.slipp.slippboard

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.github.galcyurio.fakeapi.FakeApi
import com.github.galcyurio.fakeapi.data.Post
import com.github.galcyurio.fakeapi.fakeApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_list.*
import net.slipp.slippboard.misc.PostItemAdapter

class ListActivity : AppCompatActivity() {
    private val adapter by lazy { PostItemAdapter() }
    private val fakeApi: FakeApi by lazy { fakeApi() }
    private val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        recycler.adapter = adapter
        fabAdd.setOnClickListener {
            startActivity(Intent(this, WriteActivity::class.java))
        }

        findPosts()
    }

    override fun onDestroy() {
        disposable.clear()
        super.onDestroy()
    }

    fun findPosts() {
        fakeApi.findPosts()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { showLoading() }
            .doFinally { hideLoading() }
            .subscribeBy(
                onSuccess = { addPosts(it) },
                onError = {
                    showErrorToast()
                    Log.e(javaClass.name, "findPosts onError", it)
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

    fun addPosts(posts: List<Post>) {
        adapter.add(posts)
    }
}
