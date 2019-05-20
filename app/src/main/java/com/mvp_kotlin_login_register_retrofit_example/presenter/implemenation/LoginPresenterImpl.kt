package com.mvp_kotlin_login_register_retrofit_example.presenter.implemenation


import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.util.Log
import com.mvp_kotlin_login_register_retrofit_example.webServices.Webservice
import com.mvp_kotlin_login_register_retrofit_example.model.responseModels.LoginResponseModel
import com.mvp_kotlin_login_register_retrofit_example.presenter.interfaces.ILoginPresenter
import com.mvp_kotlin_login_register_retrofit_example.view.interfaces.ILoginView
import com.google.gson.Gson
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginPresenterImpl(var iLoginView: ILoginView , val context: Context) : ILoginPresenter {

    lateinit var loginResponseModel: LoginResponseModel
    private val mPrefs: SharedPreferences by lazy { PreferenceManager.getDefaultSharedPreferences(context) }

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
                    loginResponseModel.sessionId = (response.headers().get("Set-Cookie").toString())

                    val prefsEditor = mPrefs.edit()
                    val gson = Gson()
                    val json = gson.toJson(loginResponseModel) // myObject - instance of MyObject
                    prefsEditor.putString("MyObject", json)
                    prefsEditor.putBoolean("isLogin" , true)
                    prefsEditor.apply()

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
}
