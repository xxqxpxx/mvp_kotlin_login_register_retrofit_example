package com.alphagene.model.responseModels

class ForgetPasswordResponseModel {

    /**
     * identificationKey : John
     */

    private var identificationKey: String? = null

    fun getIdentificationKey(): String? {
        return identificationKey
    }

    fun setIdentificationKey(identificationKey: String) {
        this.identificationKey = identificationKey
    }

}