package com.alphagene.view.interfaces

interface ICameraOrderView {

    fun onCameraOrderResult(result: Boolean, code: Int)
    fun onSetProgressBarVisibility(visibility: Int)
}