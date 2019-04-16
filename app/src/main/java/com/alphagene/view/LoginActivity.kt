package com.alphagene.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.alphagene.presenter.ILoginPresenter
import com.alphagene.presenter.LoginPresenterCompl
import com.alphagene.view.interfaces.ILoginView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity(), ILoginView {
    private lateinit var loginPresenter: ILoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.alphagene.R.layout.activity_login)
        setupView()
    }

    private fun setupView() {
        btn_login.setOnClickListener {
            loginPresenter.setProgressBarVisibility(View.VISIBLE)
            btn_login.isEnabled = false
            var name = input_email.text.toString()
            var passwd = input_password.text.toString()

            if (name.isEmpty() || passwd.isEmpty()) {
                Toast.makeText(this, "Please Fill the needed data", Toast.LENGTH_SHORT).show()
                btn_login.isEnabled = true
                loginPresenter.setProgressBarVisibility(View.INVISIBLE)
            } else
                loginPresenter.doLogin(name, passwd)
        }

        link_signup.setOnClickListener {
                val intent = Intent(this@LoginActivity , RegistrationActivity::class.java)
               startActivity(intent)
        }
        //init
        loginPresenter = LoginPresenterCompl(this)
        loginPresenter.setProgressBarVisibility(View.INVISIBLE)
    }

    override fun onSetProgressBarVisibility(visibility: Int) {
        progress_login.visibility = visibility
    }

    override fun onLoginResult(result: Boolean, code: Int) {
        loginPresenter.setProgressBarVisibility(View.INVISIBLE)
        if (result && code == 1) {
            Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show()

            val mPrefs = getSharedPreferences("id", Context.MODE_PRIVATE)
            val prefsEditor = mPrefs.edit()
            val gson = Gson()
            val json = gson.toJson(loginPresenter.getUserModel()) // myObject - instance of MyObject
            prefsEditor.putString("MyObject", json)
            prefsEditor.apply()

            goToHomeScreen()
        } else {
            Toast.makeText(this, "Login Fail, code = $code. Please Try Again", Toast.LENGTH_SHORT).show()
            btn_login.isEnabled = true
        }
    }

    private fun goToHomeScreen() {
        val intent = Intent(this, HomeActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        startActivity(intent)
        finish()
    }
}