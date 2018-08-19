package net.slipp.slippboard.misc

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.galcyurio.fakeapi.data.Post
import kotlinx.android.synthetic.main.item_board.view.*
import net.slipp.slippboard.R

class PostItemAdapter : RecyclerView.Adapter<PostItemAdapter.ViewHolder>() {
    private val posts = mutableListOf<Post>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_board, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = posts[position]
        with(holder.itemView) {
            title.text = post.title
        }
    }

    override fun getItemCount(): Int = posts.size

    fun add(posts: List<Post>) {
        this.posts.addAll(posts)
        notifyDataSetChanged()
    }

    fun addFirst(post: Post) {
        posts.add(0, post)
        notifyDataSetChanged()
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v)
}