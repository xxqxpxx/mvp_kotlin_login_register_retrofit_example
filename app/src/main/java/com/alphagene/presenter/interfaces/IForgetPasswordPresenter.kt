package com.alphagene.presenter.interfaces

interface IForgetPasswordPresenter {
    fun doForgetPassword(name: String)
    fun doVerificationCode(code: String)
    fun setProgressBarVisibility(visibility: Int)
    fun setVerificationCodeVisibility(visibility: Int)
}