package net.slipp.slippboard.ui.write

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_write.*
import net.slipp.slippboard.R
import net.slipp.slippboard.databinding.ActivityWriteBinding
import net.slipp.slippboard.misc.observe

class WriteActivity : AppCompatActivity() {
    private lateinit var viewModel: WriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityWriteBinding = DataBindingUtil.setContentView(this, R.layout.activity_write)
        viewModel = ViewModelProviders.of(this).get(WriteViewModel::class.java)
        binding.vm = viewModel
        binding.setLifecycleOwner(this)

        fabComplete.setOnClickListener { viewModel.write() }
        viewModel.onError.observe(this) { showErrorMessage() }
        viewModel.onCreateComplete.observe(this) { finish() }
    }

    fun showErrorMessage() {
        Toast.makeText(this, "에러 발생", Toast.LENGTH_SHORT).show()
    }
}