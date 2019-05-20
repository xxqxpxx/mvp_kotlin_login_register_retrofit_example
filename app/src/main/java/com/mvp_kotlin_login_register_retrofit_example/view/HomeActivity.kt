package com.mvp_kotlin_login_register_retrofit_example.view

import android.content.Context
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.mvp_kotlin_login_register_retrofit_example.R
import com.mvp_kotlin_login_register_retrofit_example.model.responseModels.LoginResponseModel
import com.google.gson.Gson

class HomeActivity : AppCompatActivity() {
    private lateinit var mPrefs: SharedPreferences
    private lateinit var currentuser: LoginResponseModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

     //   getUserData()
    }

    private fun getUserData() {
        mPrefs = getSharedPreferences("id", Context.MODE_PRIVATE)
        val gson = Gson()
        val json = mPrefs.getString("MyObject", "")
        currentuser = gson.fromJson<LoginResponseModel>(json, LoginResponseModel::class.java!!)
    }


}
