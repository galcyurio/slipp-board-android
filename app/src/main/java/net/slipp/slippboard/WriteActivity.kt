package net.slipp.slippboard

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.github.galcyurio.fakeapi.FakeApi
import com.github.galcyurio.fakeapi.data.Post
import com.github.galcyurio.fakeapi.fakeApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_write.*

class WriteActivity : AppCompatActivity() {
    companion object {
        const val CODE = 2222
    }

    private val fakeApi: FakeApi by lazy { fakeApi() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write)

        fabComplete.setOnClickListener {
            write(Post(
                title = etTitle.text.toString(),
                body = etBody.text.toString()
            ))
        }
    }

    fun write(post: Post) {
        fakeApi.savePost(post)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onComplete = { finishWithPost(post) },
                onError = { showErrorMessage() }
            )
    }

    fun showErrorMessage() {
        Toast.makeText(this, "에러 발생", Toast.LENGTH_SHORT).show()
    }

    fun finishWithPost(post: Post) {
        val intent = Intent()
        intent.putExtra("post", post)

        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}