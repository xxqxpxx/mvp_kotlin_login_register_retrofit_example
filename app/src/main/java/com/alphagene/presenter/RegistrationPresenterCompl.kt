package com.alphagene.presenter

import android.os.Handler
import android.os.Looper
import com.alphagene.model.IUser
import com.alphagene.model.UserModel
import com.alphagene.view.interfaces.IRegistrationView

class RegistrationPresenterCompl(private var iRegistrationView: IRegistrationView) : IRegistrationPresenter {

    private lateinit var user: IUser
    private var handler: Handler

    init {
        initUser()
        handler = Handler(Looper.getMainLooper())
    }

    override fun doRegistration(firstName: String, lastName: String, email: String, password: String, mobile: String) {
        var isLoginSuccess = true
        val code = user.checkUserValidity(name, passwd)
        if (!code.equals(0)) {
            isLoginSuccess = false
        }
        val result = isLoginSuccess
        handler.postDelayed({ iRegistrationView.onRegistrationResult(result, code) }, 5000)
    }

    override fun setProgressBarVisibility(visiblity: Int) {
        iRegistrationView.onSetProgressBarVisibility(visiblity)
    }

    private fun initUser() {
        user = UserModel("mvp", "mvp")
    }

}