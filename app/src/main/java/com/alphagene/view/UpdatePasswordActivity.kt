package com.alphagene.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.alphagene.R

class UpdatePasswordActivity : AppCompatActivity() {

    private lateinit var authenticationToken:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_password)

        authenticationToken = intent.getStringExtra("authenticationToken")


    }
}
