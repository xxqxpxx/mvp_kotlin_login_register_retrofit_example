package com.alphagene.view.interfaces

interface IRegistrationView {
    fun onRegistrationResult(result: Boolean, code: Int)
    fun onSetProgressBarVisibility(visibility: Int)
}