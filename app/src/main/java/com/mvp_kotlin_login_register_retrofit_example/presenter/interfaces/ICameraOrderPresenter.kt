package com.mvp_kotlin_login_register_retrofit_example.presenter.interfaces

interface ICameraOrderPresenter {
    fun doCameraOrder(userID: String, sessionID: String , prescriptionImage: String)
    fun setProgressBarVisibility(visibility: Int)
}