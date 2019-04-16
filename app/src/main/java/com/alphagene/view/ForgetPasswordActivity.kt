package com.alphagene.view

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.alphagene.presenter.implemenation.ForgetPasswordPresenterImpl
import com.alphagene.presenter.interfaces.IForgetPasswordPresenter
import com.alphagene.view.interfaces.IForgetPasswordView
import kotlinx.android.synthetic.main.activity_forget_password.*
import com.poovam.pinedittextfield.PinField



class ForgetPasswordActivity : AppCompatActivity(), IForgetPasswordView {

    private lateinit var iForgetPasswordPresenter: IForgetPasswordPresenter
    private lateinit var verificationCode : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.alphagene.R.layout.activity_forget_password)

        setupView()
    }

    private fun setupView() {

        btn_send_code.setOnClickListener {
            iForgetPasswordPresenter.setProgressBarVisibility(View.VISIBLE)
            btn_send_code.isEnabled = false
            val email = input_email.text.toString()

            if (email.isEmpty()) {
                Toast.makeText(this, getString(com.alphagene.R.string.please_fill_the_needed_data), Toast.LENGTH_SHORT).show()
                btn_send_code.isEnabled = true
                iForgetPasswordPresenter.setProgressBarVisibility(View.INVISIBLE)
            } else
                iForgetPasswordPresenter.doForgetPassword(email)
        }

        btn_verify_code.setOnClickListener {
            iForgetPasswordPresenter.setProgressBarVisibility(View.VISIBLE)
            btn_verify_code.isEnabled = false
            val code = verificationCode

            if (code.isEmpty()) {
                Toast.makeText(this, getString(com.alphagene.R.string.please_fill_the_needed_data), Toast.LENGTH_SHORT).show()
                btn_verify_code.isEnabled = true
                iForgetPasswordPresenter.setProgressBarVisibility(View.INVISIBLE)
            } else
                iForgetPasswordPresenter.doVerificationCode(code)
        }


        input_code.onTextCompleteListener = object : PinField.OnTextCompleteListener {
            override fun onTextComplete(enteredText: String): Boolean {
                verificationCode = enteredText
                btn_verify_code.callOnClick()
                return true // Return true to keep the keyboard open else return false to close the keyboard
            }
        }
        //init
        iForgetPasswordPresenter = ForgetPasswordPresenterImpl(this)
        iForgetPasswordPresenter.setProgressBarVisibility(View.INVISIBLE)
        iForgetPasswordPresenter.setVerificationCodeVisibility(View.INVISIBLE)
    }

    override fun onForgetPasswordResult(result: Boolean, code: Int) {
        iForgetPasswordPresenter.setProgressBarVisibility(View.INVISIBLE)
        if (result && code == 1) {
            Toast.makeText(this, getString(com.alphagene.R.string.code_sent_success), Toast.LENGTH_SHORT).show()
            iForgetPasswordPresenter.setVerificationCodeVisibility(View.VISIBLE)
        } else {
            Toast.makeText(this, getString(com.alphagene.R.string.please_try_again), Toast.LENGTH_SHORT).show()
            btn_send_code.isEnabled = true
        }
    }

    override fun onVerificationCodeResult(result: Boolean, code: Int, authenticationToken: String?) {
        iForgetPasswordPresenter.setProgressBarVisibility(View.INVISIBLE)
        if (result && code == 1) {
            Toast.makeText(this, getString(com.alphagene.R.string.Success), Toast.LENGTH_SHORT).show()
              goToUpdatePasswordScreen(authenticationToken)
        } else {
            Toast.makeText(this, getString(com.alphagene.R.string.please_try_again), Toast.LENGTH_SHORT).show()
            btn_verify_code.isEnabled = true
        }
    }

    private fun goToUpdatePasswordScreen(authenticationToken: String?) {
        val intent = Intent(this@ForgetPasswordActivity , UpdatePasswordActivity::class.java)
        intent.putExtra("authenticationToken" , authenticationToken)
        startActivity(intent)
        finish()
    }

    override fun onSetProgressBarVisibility(visibility: Int) {
        progress_forget_password.visibility = visibility
    }

    override fun onVerificationCodeVisibility(visibility: Int) {
        verificationCodeLayout.visibility = visibility
    }


}
