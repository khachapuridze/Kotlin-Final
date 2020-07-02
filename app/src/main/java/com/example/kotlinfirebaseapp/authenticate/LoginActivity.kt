package com.example.kotlinfirebaseapp.authenticate

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlinfirebaseapp.MainActivity
import com.example.kotlinfirebaseapp.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.sign_in_layout.*

class LoginActivity:AppCompatActivity() {

    lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_in_layout)
        init()
    }
    private fun init() {

        auth = FirebaseAuth.getInstance()
        if(auth.currentUser !== null){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        login_btn.setOnClickListener {
            if(TextUtils.isEmpty(email_edt_text.text.toString()) || TextUtils.isEmpty(pass_edt_text.text.toString())) {
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_LONG).show()
            } else{
                auth.signInWithEmailAndPassword(email_edt_text.text.toString(),
                    pass_edt_text.text.toString()).addOnCompleteListener(this, OnCompleteListener { task ->
                    if(task.isSuccessful) {
                        Toast.makeText(this, "Successfully Logged In", Toast.LENGTH_LONG).show()
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }else {
                        Toast.makeText(this, "Login Failed", Toast.LENGTH_LONG).show()
                    }
                })
            }
        }
        signup_btn.setOnClickListener {
            val intent = Intent(this,
                SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

}