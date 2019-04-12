package com.alphagene.presenter

interface ILoginPresenter {
    fun doLogin(name: String, passwd: String)
    fun setProgressBarVisibility(visiblity: Int)
}