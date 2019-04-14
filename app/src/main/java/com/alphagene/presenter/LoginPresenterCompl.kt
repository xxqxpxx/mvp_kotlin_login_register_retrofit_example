package com.alphagene.presenter

import com.alphagene.view.interfaces.ILoginView

class LoginPresenterCompl(private var iLoginView: ILoginView) : ILoginPresenter {


    override fun doLogin(name: String, passwd: String) {

    }

    override fun setProgressBarVisibility(visiblity: Int) {
        iLoginView.onSetProgressBarVisibility(visiblity)
    }

}
