package net.slipp.slippboard.ui.write

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.github.galcyurio.slippboardapi.SlippBoardClient
import com.github.galcyurio.slippboardapi.data.Board
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_write.*
import net.slipp.slippboard.R

class WriteActivity : AppCompatActivity() {
    companion object {
        const val CODE = 2222
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write)

        fabComplete.setOnClickListener {
            write(Board(
                title = etTitle.text.toString(),
                content = etContent.text.toString()
            ))
        }
    }

    fun write(board: Board) {
        SlippBoardClient.create(board)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onComplete = { finishWithBoard(board) },
                onError = { showErrorMessage() }
            )
    }

    fun showErrorMessage() {
        Toast.makeText(this, "에러 발생", Toast.LENGTH_SHORT).show()
    }

    fun finishWithBoard(board: Board) {
        val intent = Intent()
        intent.apply {
            putExtra("title", board.title)
            putExtra("content", board.content)
            putExtra("id", board.id)
            putExtra("username", board.username)
            putExtra("createDateTime", board.createdDateTime)
            putExtra("updatedTime", board.updatedDateTime)
        }
        // TODO: intent로 넘기지 않고 아키텍쳐 적용 후에 LiveData나 Rx의 Subject로 객체를 넘기도록 변경

        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}