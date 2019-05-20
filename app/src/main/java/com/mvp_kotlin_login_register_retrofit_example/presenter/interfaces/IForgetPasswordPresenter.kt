package com.mvp_kotlin_login_register_retrofit_example.presenter.interfaces

interface IForgetPasswordPresenter {
    fun doForgetPassword(name: String)
    fun doVerificationCode(code: String)
    fun setProgressBarVisibility(visibility: Int)
    fun setVerificationCodeVisibility(visibility: Int)
}