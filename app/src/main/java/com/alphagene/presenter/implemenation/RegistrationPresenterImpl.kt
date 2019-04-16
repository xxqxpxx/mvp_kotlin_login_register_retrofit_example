package com.alphagene.presenter.implemenation

import com.alphagene.presenter.interfaces.IRegistrationPresenter
import com.alphagene.view.interfaces.IRegistrationView

class RegistrationPresenterImpl(private var iRegistrationView: IRegistrationView) :
    IRegistrationPresenter {


    override fun doRegistration(firstName: String, lastName: String, email: String, password: String, mobile: String) {
       // TODO implement backend
    }

    override fun setProgressBarVisibility(visibility: Int) {
        iRegistrationView.onSetProgressBarVisibility(visibility)
    }
}