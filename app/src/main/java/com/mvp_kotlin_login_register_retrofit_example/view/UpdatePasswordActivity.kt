package com.mvp_kotlin_login_register_retrofit_example.view

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.mvp_kotlin_login_register_retrofit_example.R
import com.mvp_kotlin_login_register_retrofit_example.presenter.implemenation.UpdatePasswordPresenterImpl
import com.mvp_kotlin_login_register_retrofit_example.presenter.interfaces.IUpdatePasswordPresenter
import com.mvp_kotlin_login_register_retrofit_example.view.interfaces.IUpdatePasswordView
import kotlinx.android.synthetic.main.activity_update_password.*

class UpdatePasswordActivity : AppCompatActivity() , IUpdatePasswordView{

    private lateinit var updatePasswordPresenter: IUpdatePasswordPresenter
    private lateinit var authenticationToken:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_password)

        authenticationToken = intent.getStringExtra("authenticationToken")
        setupView()
    }

    private fun setupView() {
        btn_update_password.setOnClickListener {
            updatePasswordPresenter.setProgressBarVisibility(View.VISIBLE)
            btn_update_password.isEnabled = false
            val passwd = input_password.text.trim().toString()

            if ( passwd.isEmpty()) {
                // Toast.makeText(this, getString(com.alphagene.R.string.please_fill_the_needed_data), Toast.LENGTH_SHORT).show()
                btn_update_password.isEnabled = true
                input_password.background = getDrawable(R.drawable.error_fieldbackground)

                updatePasswordPresenter.setProgressBarVisibility(View.INVISIBLE)
            } else
                updatePasswordPresenter.doUpdatePassword(passwd, authenticationToken)
        }

        // init
        updatePasswordPresenter = UpdatePasswordPresenterImpl(this)
        updatePasswordPresenter.setProgressBarVisibility(View.INVISIBLE)

    }

    override fun onUpdatePasswordResult(result: Boolean, code: Int) {
        updatePasswordPresenter.setProgressBarVisibility(View.INVISIBLE)
        if (result && code == 1) {
            // Toast.makeText(this,  getString(com.alphagene.R.string.Success), Toast.LENGTH_SHORT).show()
            goToLoginScreen()
        } else {
            Toast.makeText(this,  getString(R.string.please_try_again), Toast.LENGTH_SHORT).show()
            btn_update_password.isEnabled = true
        }
    }

    private fun goToLoginScreen() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onSetProgressBarVisibility(visibility: Int) {
        progress_update_password.visibility = visibility
    }


}
