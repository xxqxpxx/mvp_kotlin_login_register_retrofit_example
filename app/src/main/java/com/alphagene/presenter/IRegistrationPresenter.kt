package com.alphagene.presenter

interface IRegistrationPresenter {
    fun doRegistration(firstName: String, lastName: String, email: String, password: String, mobile: String)
    fun setProgressBarVisibility(visiblity: Int)
}