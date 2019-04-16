package com.alphagene.model.responseModels

class AuthenticationCodeResponseModel {

    /**
     * authenticationToken : John
     */

    private var authenticationToken: String? = null

    fun getAuthenticationToken(): String? {
        return authenticationToken
    }

    fun setAuthenticationToken (authenticationToken: String) {
        this.authenticationToken = authenticationToken
    }


}