package com.alphagene.WebServices

import com.alphagene.model.responseModels.LoginResponseModel
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface Api {

    @POST("login")
    fun login(@Body headers: RequestBody): Call<LoginResponseModel>

    @POST("forgot-password")
    fun forgot_password(@Body headers: RequestBody): Call<LoginResponseModel>

    @POST("reset-password-authentication-code")
    fun verify_authentication_code(@Body headers: RequestBody): Call<LoginResponseModel>

    @POST("reset-password")
    fun reset_password(@Body headers: RequestBody): Call<LoginResponseModel>


}