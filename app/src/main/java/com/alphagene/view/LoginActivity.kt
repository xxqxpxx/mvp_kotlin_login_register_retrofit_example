package com.alphagene.view

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.alphagene.presenter.interfaces.ILoginPresenter
import com.alphagene.presenter.implemenation.LoginPresenterImpl
import com.alphagene.view.interfaces.ILoginView
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
            val name = input_email.text.toString()
            val passwd = input_password.text.toString()

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
            finish()
        }

        link_forget_password.setOnClickListener {
                 val intent = Intent(this@LoginActivity , ForgetPasswordActivity::class.java)
               startActivity(intent)
            finish()
        }

        //init
        loginPresenter = LoginPresenterImpl(this)
        loginPresenter.setProgressBarVisibility(View.INVISIBLE)
    }

    override fun onSetProgressBarVisibility(visibility: Int) {
        progress_login.visibility = visibility
    }

    override fun onLoginResult(result: Boolean, code: Int) {
        loginPresenter.setProgressBarVisibility(View.INVISIBLE)
        if (result && code == 1) {
            Toast.makeText(this,  getString(com.alphagene.R.string.Success), Toast.LENGTH_SHORT).show()
            //   goToHomeScreen()
        } else {
            Toast.makeText(this,  getString(com.alphagene.R.string.please_try_again), Toast.LENGTH_SHORT).show()
            btn_login.isEnabled = true
            input_password.text.clear()
        }
    }

    private fun goToHomeScreen() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}