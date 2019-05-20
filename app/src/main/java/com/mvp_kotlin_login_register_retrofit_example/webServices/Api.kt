package com.mvp_kotlin_login_register_retrofit_example.webServices

import com.mvp_kotlin_login_register_retrofit_example.model.responseModels.AuthenticationCodeResponseModel
import com.mvp_kotlin_login_register_retrofit_example.model.responseModels.ForgetPasswordResponseModel
import com.mvp_kotlin_login_register_retrofit_example.model.responseModels.LoginResponseModel
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface Api {

    @POST("login")
    fun login(@Body headers: RequestBody): Call<LoginResponseModel>

    @POST("forgot-password")
    fun forgot_password(@Body headers: RequestBody): Call<ForgetPasswordResponseModel>

    @POST("validate-password-reset-code")
    fun verify_authentication_code(@Body headers: RequestBody): Call<AuthenticationCodeResponseModel>

    @POST("reset-password")
    fun reset_password(@Body headers: RequestBody): Call<ResponseBody>

    @POST("register")
    fun register(@Body headers: RequestBody): Call<LoginResponseModel>

    @POST("submit-order")
    fun submitOrder(@Body headers: RequestBody): Call<ResponseBody>

}