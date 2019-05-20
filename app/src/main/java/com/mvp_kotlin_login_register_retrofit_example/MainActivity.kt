package com.mvp_kotlin_login_register_retrofit_example

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.mvp_kotlin_login_register_retrofit_example.view.LoginActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}
