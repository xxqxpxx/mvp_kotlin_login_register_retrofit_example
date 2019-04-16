package com.alphagene.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.alphagene.R
import com.alphagene.model.responseModels.LoginResponseModel
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    private lateinit var mPrefs: SharedPreferences
    private lateinit var currentuser: LoginResponseModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

     //   getUserData()
        setupView()
    }

    private fun getUserData() {
        mPrefs = getSharedPreferences("id", Context.MODE_PRIVATE)
        val gson = Gson()
        val json = mPrefs.getString("MyObject", "")
        currentuser = gson.fromJson<LoginResponseModel>(json, LoginResponseModel::class.java!!)
    }

    private fun setupView() {
        btn_orderByPhoto.setOnClickListener {
            val intent = Intent(this, CameraOrderingActivity::class.java)
            startActivity(intent)
        }

        btn_orderManually.setOnClickListener {
            val intent = Intent(this, ManualOrderingActivity::class.java)
            startActivity(intent)
        }
     }
}
