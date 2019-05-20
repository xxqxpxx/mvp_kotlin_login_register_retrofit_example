package com.mvp_kotlin_login_register_retrofit_example.presenter.interfaces

interface IRegistrationPresenter {
    fun doRegistration(firstName: String, lastName: String, email: String, password: String, mobile: String, city: String, street: String, building: String, floor: String, apartment: String )
    fun setProgressBarVisibility(visibility: Int)
}