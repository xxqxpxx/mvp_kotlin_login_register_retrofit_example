package com.alphagene.view.interfaces

interface IRegistrationView {
    fun onRegistrationResult(result: Boolean, code: Boolean)
    fun onSetProgressBarVisibility(visibility: Int)
}