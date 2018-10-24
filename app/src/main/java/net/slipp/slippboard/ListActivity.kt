package net.slipp.slippboard

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_list.*

class ListActivity : AppCompatActivity() {
    val mAuth = FirebaseAuth.getInstance()
    var mInputArray = ArrayList<UserDto>()
    var mAadapter = UsersAdapter(generateData())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        initBannerAnimation()

        val layoutManager = LinearLayoutManager(applicationContext, LinearLayout.VERTICAL, false)
        recyclerView?.layoutManager = layoutManager
        recyclerView?.itemAnimator = DefaultItemAnimator()
        recyclerView?.adapter = mAadapter
        mAadapter.notifyDataSetChanged()
        swipe_layout.setOnRefreshListener {
            mAadapter.notifyDataSetChanged()
            swipe_layout.isRefreshing = false
        }

        addItem()
        removeItem()

    }

    private fun initBannerAnimation() {
        val view1 = layoutInflater.inflate(R.layout.first_tab_layout, null)
        val view2 = layoutInflater.inflate(R.layout.second_tab_layout, null)
        val view3 = layoutInflater.inflate(R.layout.third_tab_layout, null)
        var views = arrayListOf(view1, view2, view3)

        var titles = arrayListOf("테스트1", "테스트2", "테스트3")
        decent_banner.start(views, titles, 2, 500, R.drawable.common_empty_people_ic)
    }

    private fun addItem() {
        btn_plus.setOnClickListener() {
            val dialogBuilder = AlertDialog.Builder(this)
            val inflater = this.layoutInflater
            val dialogView = inflater.inflate(R.layout.custom_dialog, null)

            with(dialogBuilder) {
                setView(dialogView)
                setTitle("Add New")
                setMessage("Enter Title and Comment.")

                setPositiveButton("Save") { dialog, whichButton ->
                    mAadapter = UsersAdapter(addData(dialogView))
                    mAadapter.notifyDataSetChanged()
                    dialog.dismiss()
                }

                setNegativeButton("Cancel") { dialog, whichButton ->
                    dialog.dismiss()
                }

            }

            val b = dialogBuilder.create()
            b.show()
        }
    }

    private fun addData(dialogView: View): ArrayList<UserDto> {
        var title = dialogView.findViewById<EditText>(R.id.edt_title)
        var comment = dialogView.findViewById<EditText>(R.id.edt_comment)
        mInputArray.add(UserDto(title.text.toString(), comment.text.toString()))
        return mInputArray
    }

    private fun removeItem() {

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                mInputArray.removeAt(viewHolder.position)
                mAadapter.notifyDataSetChanged()
            }
        }).attachToRecyclerView(recyclerView)
    }


    fun generateData(): ArrayList<UserDto> {

        for (i in 0..5) {
            var user = UserDto("Example " + i, "TestTest :) \n testset \n te")
            mInputArray.add(user)
        }

        return mInputArray
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == R.id.signOut) {
            mAuth.signOut()
            startActivity(Intent(this, MainActivity::class.java))
            Toast.makeText(this, "로그아웃됨", Toast.LENGTH_LONG).show()
        }
        return super.onOptionsItemSelected(item)
    }


}