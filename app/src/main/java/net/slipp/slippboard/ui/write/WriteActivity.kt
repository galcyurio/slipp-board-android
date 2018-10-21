package net.slipp.slippboard.ui.write

import android.app.Activity
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.github.galcyurio.slippboardapi.data.Board
import kotlinx.android.synthetic.main.activity_write.*
import net.slipp.slippboard.R
import net.slipp.slippboard.databinding.ActivityWriteBinding
import net.slipp.slippboard.misc.observe

class WriteActivity : AppCompatActivity() {
    companion object {
        const val CODE = 2222
    }

    private lateinit var viewModel: WriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityWriteBinding = DataBindingUtil.setContentView(this, R.layout.activity_write)
        viewModel = ViewModelProviders.of(this).get(WriteViewModel::class.java)
        binding.vm = viewModel
        binding.setLifecycleOwner(this)

        fabComplete.setOnClickListener { viewModel.write() }
        viewModel.onError.observe(this) { showErrorMessage() }
        viewModel.onCreateComplete.observe(this) { finishWithBoard(it) }
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