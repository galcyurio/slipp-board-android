package net.slipp.slippboard

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_list.*
import net.slipp.slippboard.misc.PostItemAdapter

class ListActivity : AppCompatActivity() {
    private val adapter by lazy { PostItemAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        recycler.adapter = adapter
    }
}
