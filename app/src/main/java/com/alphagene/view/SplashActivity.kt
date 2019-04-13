package com.alphagene.view

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.alphagene.MainActivity
import android.os.Handler


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val handler = Handler()
        handler.postDelayed({ finish() }, 1000)

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
