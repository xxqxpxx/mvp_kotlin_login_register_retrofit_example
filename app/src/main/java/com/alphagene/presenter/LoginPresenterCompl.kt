package com.alphagene.presenter

import android.os.Handler
import android.os.Looper
import com.alphagene.model.IUser
import com.alphagene.model.UserModel
import com.alphagene.view.interfaces.ILoginView

class LoginPresenterCompl : ILoginPresenter {

    var iLoginView: ILoginView
    lateinit var user: IUser
    var handler: Handler

    constructor(iLoginView: ILoginView) {
        this.iLoginView = iLoginView
        initUser()
        handler = Handler(Looper.getMainLooper());
    }

    override fun doLogin(name: String, passwd: String) {
        var isLoginSuccess = true
        val code = user.checkUserValidity(name, passwd)
        if (code != 0)
            isLoginSuccess = false
        val result = isLoginSuccess
        handler.postDelayed({ iLoginView.onLoginResult(result, code) }, 5000)
    }

    override fun setProgressBarVisiblity(visiblity: Int) {
        iLoginView.onSetProgressBarVisibility(visiblity)
    }

    private fun initUser() {
        user = UserModel("mvp", "mvp")
    }
}
