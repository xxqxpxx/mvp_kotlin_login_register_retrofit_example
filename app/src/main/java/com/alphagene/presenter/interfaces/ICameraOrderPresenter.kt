package com.alphagene.presenter.interfaces

interface ICameraOrderPresenter {
    fun doCameraOrder(userID: String, sessionID: String , prescriptionImage: String)
    fun setProgressBarVisibility(visibility: Int)
}