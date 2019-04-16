package com.alphagene.presenter.interfaces

import com.alphagene.model.responseModels.LoginResponseModel

interface ILoginPresenter {
    fun doLogin(name: String, passwd: String)
    fun setProgressBarVisibility(visibility: Int)
     fun getUserModel(): LoginResponseModel
}