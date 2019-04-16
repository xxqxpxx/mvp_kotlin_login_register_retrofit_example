package com.alphagene.presenter.interfaces

interface IForgetPasswordPresenter {
    fun doForgetPassword(name: String)
    fun doVerificationCode(name: String)
    fun setProgressBarVisibility(visibility: Int)
    fun setVerificationCodeVisibility(visibility: Int)
}