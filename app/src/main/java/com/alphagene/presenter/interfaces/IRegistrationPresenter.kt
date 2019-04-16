package com.alphagene.presenter.interfaces

interface IRegistrationPresenter {
    fun doRegistration(firstName: String, lastName: String, email: String, password: String, mobile: String)
    fun setProgressBarVisibility(visibility: Int)
}