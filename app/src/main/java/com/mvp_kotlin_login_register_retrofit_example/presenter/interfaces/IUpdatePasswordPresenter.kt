package com.mvp_kotlin_login_register_retrofit_example.presenter.interfaces

interface IUpdatePasswordPresenter {
    fun doUpdatePassword(password: String, authenticationToken: String  )
    fun setProgressBarVisibility(visibility: Int)
}