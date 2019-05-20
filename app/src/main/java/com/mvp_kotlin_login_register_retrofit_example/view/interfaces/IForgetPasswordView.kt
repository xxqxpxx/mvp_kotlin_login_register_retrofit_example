package com.mvp_kotlin_login_register_retrofit_example.view.interfaces

interface IForgetPasswordView {
    fun onForgetPasswordResult(result: Boolean, code: Int)
    fun onVerificationCodeResult(result: Boolean, code: Int, authenticationToken: String?)
    fun onSetProgressBarVisibility(visibility: Int)
    fun onVerificationCodeVisibility(visibility: Int)
}