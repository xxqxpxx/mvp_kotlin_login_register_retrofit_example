package com.alphagene.presenter.interfaces

interface IUpdatePasswordPresenter {
    fun doUpdatePassword(password: String, authenticationToken: String  )
    fun setProgressBarVisibility(visibility: Int)
}