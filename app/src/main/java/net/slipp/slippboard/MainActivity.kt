package net.slipp.slippboard

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val mAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_login.setOnClickListener(View.OnClickListener { view ->
            login()
        })

        btn_register.setOnClickListener(){ view ->
            register()
        }
    }

    private fun login() {
        val email = et_email.text.toString()
        val password = et_password.text.toString()

        if (!email.isEmpty() && !password.isEmpty()) {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this,
                    OnCompleteListener { task ->
                        if (task.isSuccessful) {
                            startActivity(Intent(this, ListActivity :: class.java))
                            Toast.makeText(this, "로그인 성공!", Toast.LENGTH_LONG).show()
                        } else   Toast.makeText(this, "로그인 실패!", Toast.LENGTH_LONG).show()
                    })

        } else Toast.makeText(this, "모든 정보 입력 필요", Toast.LENGTH_LONG).show()

    }

    private fun register() {
        startActivity(Intent(this, RegisterActivity :: class.java))
    }
}
