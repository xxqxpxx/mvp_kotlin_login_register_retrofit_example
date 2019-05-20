package com.mvp_kotlin_login_register_retrofit_example.view.interfaces

interface IRegistrationView {
    fun onRegistrationResult(result: Boolean, code: Int, identificationToken: String)
    fun onSetProgressBarVisibility(visibility: Int)
}