package com.alphagene.presenter

import android.content.Context
import android.preference.PreferenceManager
import android.util.Log
import com.alphagene.WebServices.Webservice
import com.alphagene.model.responseModels.LoginResponseModel
import com.alphagene.view.LoginActivity
import com.alphagene.view.interfaces.ILoginView
import com.google.gson.Gson
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginPresenterCompl(var iLoginView: ILoginView) : ILoginPresenter {

    lateinit var loginResponseModel: LoginResponseModel

    override fun doLogin(name: String, passwd: String) {
        val data = HashMap<String, String>()
        data["identity"] = name
        data["password"] = passwd

        val body = RequestBody.create(
            okhttp3.MediaType.parse("application/json; charset=utf-8"),
            JSONObject(data).toString()
        )

        Webservice.getInstance().getApi().login(body).enqueue(object : Callback<LoginResponseModel> {
            override fun onResponse(call: Call<LoginResponseModel>, response: Response<LoginResponseModel>) =
                if (!response.isSuccessful) {
                    try {
                        //    val jObjError = JSONObject(response.errorBody()!!.string())
                        iLoginView.onLoginResult(false, -1)
                    } catch (e: Exception) {
                        iLoginView.onLoginResult(false, -1)
                    }
                } else {
                    loginResponseModel = response.body()!!
                    iLoginView.onLoginResult(true, 1)

                }

            override fun onFailure(call: Call<LoginResponseModel>, t: Throwable) {
                Log.e("login", "onFailure: ", t)
                iLoginView.onLoginResult(false, 0)
            }
        })
    }

    override fun setProgressBarVisibility(visibility: Int) {
        iLoginView.onSetProgressBarVisibility(visibility)
    }

    override fun getUserModel(): LoginResponseModel {
        return this.loginResponseModel
    }

}
