package com.alphagene.presenter

interface IForgetPasswordPresenter {
    fun doForgetPassword(name: String, passwd: String)
    fun doVerificationCode(name: String, passwd: String)
    fun setProgressBarVisibility(visibility: Int)
}