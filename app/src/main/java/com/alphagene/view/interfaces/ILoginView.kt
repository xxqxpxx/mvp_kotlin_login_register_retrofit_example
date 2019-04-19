package com.alphagene.view.interfaces

 interface ILoginView {
    fun onLoginResult(result: Boolean, code: Int)
    fun onSetProgressBarVisibility(visibility: Int)
}