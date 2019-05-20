package com.mvp_kotlin_login_register_retrofit_example.presenter.implemenation

import android.util.Log
import com.mvp_kotlin_login_register_retrofit_example.webServices.Webservice
import com.mvp_kotlin_login_register_retrofit_example.model.responseModels.AuthenticationCodeResponseModel
import com.mvp_kotlin_login_register_retrofit_example.model.responseModels.ForgetPasswordResponseModel
import com.mvp_kotlin_login_register_retrofit_example.presenter.interfaces.IForgetPasswordPresenter
import com.mvp_kotlin_login_register_retrofit_example.view.interfaces.IForgetPasswordView
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForgetPasswordPresenterImpl(var iforgetPasswordView: IForgetPasswordView) : IForgetPasswordPresenter {

    lateinit var forgetPasswordResponseModel: ForgetPasswordResponseModel
    lateinit var authenticationCodeResponseModel: AuthenticationCodeResponseModel

    override fun doForgetPassword(name: String) {
        val data = HashMap<String, String>()
        data["identity"] = name

        val body = RequestBody.create(
            okhttp3.MediaType.parse("application/json; charset=utf-8"),
            JSONObject(data).toString()
        )

        Webservice.getInstance().getApi().forgot_password(body).enqueue(object : Callback<ForgetPasswordResponseModel> {
            override fun onResponse(call: Call<ForgetPasswordResponseModel>, response: Response<ForgetPasswordResponseModel>) =
                if (!response.isSuccessful) {
                    //    val jObjError = JSONObject(response.errorBody()!!.string())
                    iforgetPasswordView.onForgetPasswordResult(false, -1)
                } else {
                    forgetPasswordResponseModel = response.body()!!
                    iforgetPasswordView.onForgetPasswordResult(true, 1)
                }

            override fun onFailure(call: Call<ForgetPasswordResponseModel>, t: Throwable) {
                Log.e("login", "onFailure: ", t)
                iforgetPasswordView.onForgetPasswordResult(false, 0)
            }
        })

    }

    override fun doVerificationCode(code: String) {
        val data = HashMap<String, String>()
        data["identificationToken"] = forgetPasswordResponseModel.identificationToken.toString()
        data["authenticationCode"] = code

        val body = RequestBody.create(
            okhttp3.MediaType.parse("application/json; charset=utf-8"),
            JSONObject(data).toString()
        )

        Webservice.getInstance().getApi().verify_authentication_code(body)
            .enqueue(object : Callback<AuthenticationCodeResponseModel> {
                override fun onResponse(call: Call<AuthenticationCodeResponseModel>, response: Response<AuthenticationCodeResponseModel>) =
                    if (!response.isSuccessful) {
                        iforgetPasswordView.onVerificationCodeResult(false, -1, null)
                    } else {
                        authenticationCodeResponseModel = response.body()!!
                        iforgetPasswordView.onVerificationCodeResult(true, 1 , authenticationCodeResponseModel.authenticationToken.toString() )
                    }

                override fun onFailure(call: Call<AuthenticationCodeResponseModel>, t: Throwable) {
                    Log.e("login", "onFailure: ", t)
                    iforgetPasswordView.onVerificationCodeResult(false, 0, null)
                }
            })
    }

    override fun setProgressBarVisibility(visibility: Int) {
        iforgetPasswordView.onSetProgressBarVisibility(visibility)
    }

    override fun setVerificationCodeVisibility(visibility: Int) {
        iforgetPasswordView.onVerificationCodeVisibility(visibility)
    }
}
