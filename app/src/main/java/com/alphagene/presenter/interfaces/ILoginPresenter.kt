package com.alphagene.presenter.interfaces

interface ILoginPresenter {
    fun doLogin(name: String, passwd: String)
    fun setProgressBarVisibility(visibility: Int)
}