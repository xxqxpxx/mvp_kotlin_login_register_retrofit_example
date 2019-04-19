package com.alphagene.view.interfaces

interface IUpdatePasswordView {
    fun onUpdatePasswordResult(result: Boolean, code: Int)
    fun onSetProgressBarVisibility(visibility: Int)
}