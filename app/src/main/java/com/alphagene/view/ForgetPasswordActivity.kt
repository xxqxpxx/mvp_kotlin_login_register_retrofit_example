package com.alphagene.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.alphagene.R
import com.alphagene.presenter.ForgetPasswordPresenterCompl
import com.alphagene.presenter.IForgetPasswordPresenter
import com.alphagene.presenter.ILoginPresenter
import com.alphagene.presenter.LoginPresenterCompl
import com.alphagene.view.interfaces.IForgetPasswordView

class ForgetPasswordActivity : AppCompatActivity() , IForgetPasswordView{

    private lateinit var IForgetPasswordPresenter: ILoginPresenter

    override fun onForgetPasswordResult(result: Boolean, code: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onVerificationCodeResult(result: Boolean, code: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSetProgressBarVisibility(visibility: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password)

        loginPresenter = ForgetPasswordPresenterCompl(this)
        loginPresenter.setProgressBarVisibility(View.INVISIBLE)
    }
}
