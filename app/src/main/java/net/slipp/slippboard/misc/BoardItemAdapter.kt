package net.slipp.slippboard.misc

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.galcyurio.slippboardapi.data.Board
import kotlinx.android.synthetic.main.item_board.view.*
import net.slipp.slippboard.R

class BoardItemAdapter : RecyclerView.Adapter<BoardItemAdapter.ViewHolder>() {
    private val boards = mutableListOf<Board>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_board, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val board = boards[position]
        with(holder.itemView) {
            title.text = board.title
        }
    }

    override fun getItemCount(): Int = boards.size

    fun clearAndAdd(boards: List<Board>) {
        this.boards.clear()
        this.boards.addAll(boards)
        notifyDataSetChanged()
    }

    fun add(boards: List<Board>) {
        this.boards.addAll(boards)
        notifyDataSetChanged()
    }

    fun addFirst(board: Board) {
        boards.add(0, board)
        notifyDataSetChanged()
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v)
}