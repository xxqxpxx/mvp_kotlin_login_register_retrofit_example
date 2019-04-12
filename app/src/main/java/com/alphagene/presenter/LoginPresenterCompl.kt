package com.alphagene.presenter

import android.os.Handler
import android.os.Looper
import com.alphagene.model.IUser
import com.alphagene.model.UserModel
import com.alphagene.view.interfaces.ILoginView

class LoginPresenterCompl(private var iLoginView: ILoginView) : ILoginPresenter {

    private lateinit var user: IUser
    private var handler: Handler

    init {
        initUser()
        handler = Handler(Looper.getMainLooper())
    }

    override fun doLogin(name: String, passwd: String) {
        var isLoginSuccess = true
        val code = user.checkUserValidity(name, passwd)
        if (!code.equals(0)) {
            isLoginSuccess = false
        }
        val result = isLoginSuccess
        handler.postDelayed({ iLoginView.onLoginResult(result, code) }, 5000)
    }

    override fun setProgressBarVisibility(visiblity: Int) {
        iLoginView.onSetProgressBarVisibility(visiblity)
    }

    private fun initUser() {
        user = UserModel("mvp", "mvp")
    }
}
