package net.slipp.slippboard.ui.list

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_list.*
import net.slipp.slippboard.R
import net.slipp.slippboard.databinding.ActivityListBinding
import net.slipp.slippboard.misc.BoardItemAdapter
import net.slipp.slippboard.misc.observe
import net.slipp.slippboard.ui.write.WriteActivity

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
            startActivity(Intent(this, WriteActivity::class.java))
        }
    }

    fun showErrorToast() {
        Toast.makeText(this, "에러 발생", Toast.LENGTH_SHORT).show()
    }
}
