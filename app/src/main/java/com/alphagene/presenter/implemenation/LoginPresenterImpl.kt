package com.alphagene.presenter.implemenation


import android.util.Log
import com.alphagene.webServices.Webservice
import com.alphagene.model.responseModels.LoginResponseModel
import com.alphagene.presenter.interfaces.ILoginPresenter
import com.alphagene.view.interfaces.ILoginView
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginPresenterImpl(var iLoginView: ILoginView) : ILoginPresenter {

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
                    iLoginView.onLoginResult(false, -1)
                } else {
                    loginResponseModel = response.body()!!
                    loginResponseModel.setSessionId(response.headers().get("Set-Cookie").toString())
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
