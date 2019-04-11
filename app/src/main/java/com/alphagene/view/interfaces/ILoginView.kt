package com.alphagene.view.interfaces

 interface ILoginView {
    fun onLoginResult(result: Boolean, code: Boolean)
    fun onSetProgressBarVisibility(visibility: Int)
}