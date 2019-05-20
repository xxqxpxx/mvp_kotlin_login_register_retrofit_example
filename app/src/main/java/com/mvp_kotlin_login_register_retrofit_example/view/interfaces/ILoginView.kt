package com.mvp_kotlin_login_register_retrofit_example.view.interfaces

interface ILoginView {
    fun onLoginResult(result: Boolean, code: Int)
    fun onSetProgressBarVisibility(visibility: Int)
}