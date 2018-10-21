package net.slipp.slippboard.ui.list

import android.app.Activity
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.github.galcyurio.slippboardapi.data.Board
import kotlinx.android.synthetic.main.activity_list.*
import net.slipp.slippboard.R
import net.slipp.slippboard.databinding.ActivityListBinding
import net.slipp.slippboard.misc.BoardItemAdapter
import net.slipp.slippboard.misc.observe
import net.slipp.slippboard.ui.write.WriteActivity
import java.util.*

class ListActivity : AppCompatActivity() {
    private val adapter by lazy { BoardItemAdapter() }
    private lateinit var viewModel: ListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityListBinding = DataBindingUtil.setContentView(this, R.layout.activity_list)
        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)
        binding.vm = viewModel
        binding.setLifecycleOwner(this)

        viewModel.boards.observe(this) { adapter.clearAndAdd(it) }
        viewModel.onError.observe(this) { showErrorToast() }
        viewModel.findBoards()

        recycler.adapter = adapter
        fabAdd.setOnClickListener {
            val intent = Intent(this, WriteActivity::class.java)
            startActivityForResult(intent, WriteActivity.CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode != Activity.RESULT_OK) return

        when (requestCode) {
            WriteActivity.CODE -> {
                data?.apply {
                    Board(
                        title = getStringExtra("title"),
                        content = getStringExtra("content"),
                        id = getLongExtra("id", -1),
                        username = getStringExtra("username"),
                        createdDateTime = Date(getLongExtra("createDateTime", -1)),
                        updatedDateTime = Date(getLongExtra("updatedTime", -1))
                    ).also { adapter.addFirst(it) }
                }
            }
        }
    }

    fun showErrorToast() {
        Toast.makeText(this, "에러 발생", Toast.LENGTH_SHORT).show()
    }
}
