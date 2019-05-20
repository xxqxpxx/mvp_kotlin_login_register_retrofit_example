package com.mvp_kotlin_login_register_retrofit_example.view.interfaces

interface IUpdatePasswordView {
    fun onUpdatePasswordResult(result: Boolean, code: Int)
    fun onSetProgressBarVisibility(visibility: Int)
}