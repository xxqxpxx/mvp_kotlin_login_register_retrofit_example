package com.alphagene.model.responseModels

class ForgetPasswordResponseModel {

    /**
     * identificationToken : John
     */

    private var identificationToken: String? = null

    fun getIidentificationToken(): String? {
        return identificationToken
    }

    fun setIdentificationKey(identificationKey: String) {
        this.identificationToken = identificationKey
    }

}