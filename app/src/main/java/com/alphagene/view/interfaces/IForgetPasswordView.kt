package com.alphagene.view.interfaces

interface IForgetPasswordView {
    fun onForgetPasswordResult(result: Boolean, code: Int)
    fun onVerificationCodeResult(result: Boolean, code: Int)
    fun onSetProgressBarVisibility(visibility: Int)
}