package com.mvp_kotlin_login_register_retrofit_example.view

import android.support.v7.app.AppCompatActivity
import com.mvp_kotlin_login_register_retrofit_example.presenter.interfaces.ILoginPresenter
import com.mvp_kotlin_login_register_retrofit_example.view.interfaces.ILoginView
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.mvp_kotlin_login_register_retrofit_example.presenter.implemenation.LoginPresenterImpl
import kotlinx.android.synthetic.main.activity_login.*
import com.mvp_kotlin_login_register_retrofit_example.R.*


open class LoginActivity : AppCompatActivity(), ILoginView {
    private lateinit var loginPresenter: ILoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_login)
        setupView()
    }

    private fun setupView() {

        btn_login.setOnClickListener {
            btn_login.hideKeyboard()
            loginPresenter.setProgressBarVisibility(View.VISIBLE)
            btn_login.isEnabled = false
            val name = input_mobile.text.toString().trim()
            val passwd = input_password.text.toString().trim()

            if (areFieldsValid(name , passwd)) {
                loginPresenter.doLogin(name, passwd)
            }
        }

        link_signup.setOnClickListener {
            val intent = Intent(this@LoginActivity , RegistrationActivity::class.java)
            startActivity(intent)
        }

        //init
        loginPresenter = LoginPresenterImpl(this , this)
        loginPresenter.setProgressBarVisibility(View.INVISIBLE)
    }

    private fun areFieldsValid(name: String, passwd: String): Boolean {
        if (name.isEmpty() || passwd.isEmpty()) {
            //  // Toast.makeText(this, R.string.please_fill_the_needed_data, Toast.LENGTH_SHORT).show()
            btn_login.isEnabled = true
            loginPresenter.setProgressBarVisibility(View.INVISIBLE)

            if (name.isEmpty()) {

                phone_layout_background.background = getDrawable(drawable.error_fieldbackground)
                ic_name_error.visibility = View.VISIBLE
/*             params.width = width
                params.height = hight
                phone_layout.layoutParams = params*/

                input_mobile.requestFocus()
            }

            if (passwd.isEmpty()) {
                password_layout_background.background = getDrawable(drawable.error_fieldbackground)
                ic_pass_error.visibility = View.VISIBLE
            }
            return false
        }

        else
            return true
    }

    override fun onSetProgressBarVisibility(visibility: Int) {
        progress_login.visibility = visibility
    }

    override fun onLoginResult(result: Boolean, code: Int) {
        loginPresenter.setProgressBarVisibility(View.INVISIBLE)
        if (result && code == 1) {
            //     // Toast.makeText(this,  getString(com.alphagene.R.string.Success), Toast.LENGTH_SHORT).show()
            goToHomeScreen()
        } else {
            Toast.makeText(this,  getString(string.please_try_again), Toast.LENGTH_SHORT).show()
            btn_login.isEnabled = true
            input_password.text.clear()
        }
    }

    private fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    private fun goToHomeScreen() {
        val intent = Intent(this, HomeActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        startActivity(intent)
        finish()
    }

}