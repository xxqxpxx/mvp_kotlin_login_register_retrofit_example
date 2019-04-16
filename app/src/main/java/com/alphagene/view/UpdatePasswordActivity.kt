package com.alphagene.view

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.alphagene.R
import com.alphagene.presenter.implemenation.UpdatePasswordPresenterImpl
import com.alphagene.presenter.interfaces.IUpdatePasswordPresenter
import com.alphagene.view.interfaces.IUpdatePasswordView
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
                val passwd = input_password.text.toString()

                if ( passwd.isEmpty()) {
                    Toast.makeText(this, "Please Fill the needed data", Toast.LENGTH_SHORT).show()
                    btn_update_password.isEnabled = true
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
            Toast.makeText(this,  getString(com.alphagene.R.string.Success), Toast.LENGTH_SHORT).show()
               goToLoginScreen()
        } else {
            Toast.makeText(this,  getString(com.alphagene.R.string.please_try_again), Toast.LENGTH_SHORT).show()
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
