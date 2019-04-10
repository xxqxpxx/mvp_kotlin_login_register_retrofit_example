package com.alphagene.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.alphagene.R
import com.alphagene.presenter.ILoginPresenter
import com.alphagene.presenter.LoginPresenterCompl
import com.alphagene.view.interfaces.ILoginView
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity(), View.OnClickListener , ILoginView {

    lateinit var loginPresenter: ILoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.alphagene.R.layout.activity_login)

        setupView()
    }

    private fun setupView() {
        //set listener
        btn_login.setOnClickListener(this)

        //init
        loginPresenter = LoginPresenterCompl(this)
        loginPresenter.setProgressBarVisiblity(View.INVISIBLE)

    }


    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_login -> {
                loginPresenter.setProgressBarVisiblity(View.VISIBLE)
                btn_login.isEnabled = false
                var name = input_email.text.toString()
                var passwd = input_password.text.toString()
                loginPresenter.doLogin(name,passwd)
            }
        }
    }

    override fun onSetProgressBarVisibility(visibility: Int) {
        progress_login.visibility = visibility
    }

    override fun onLoginResult(result: Boolean, code: Int) {
        loginPresenter.setProgressBarVisiblity(View.INVISIBLE)
        btn_login.isEnabled = true

        if (result) {
            Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show()
         //   goToHomeScreen()
        } else
            Toast.makeText(this, "Login Fail, code = $code", Toast.LENGTH_SHORT).show()
    }

    private fun goToHomeScreen() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
