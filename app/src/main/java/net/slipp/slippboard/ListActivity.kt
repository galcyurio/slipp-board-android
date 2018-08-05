package net.slipp.slippboard

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_list.*
import net.slipp.slippboard.data.Board
import net.slipp.slippboard.misc.BoardItemAdapter

class ListActivity : AppCompatActivity() {
    private val adapter by lazy { BoardItemAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        recycler.adapter = adapter

        /* TODO: 더미 데이터 제거할 것 */
        adapter.add((1..30)
            .map { Board("dummy title $it", "dummy content $it") })
    }
}
