package com.alphagene.presenter

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.alphagene.WebServices.Webservice
import com.alphagene.model.IUser
import com.alphagene.model.responseModels.LoginResponseModel
import com.alphagene.view.interfaces.ILoginView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import org.json.JSONObject
import okhttp3.RequestBody



class LoginPresenterCompl : ILoginPresenter {

    private  var RequestCode:Int = -1
    var iLoginView: ILoginView
    var handler: Handler
    lateinit var loginResponseModel:LoginResponseModel

    constructor(iLoginView: ILoginView) {
        this.iLoginView = iLoginView
  //      initUser()
        handler = Handler(Looper.getMainLooper());
    }

    override fun doLogin(name: String, passwd: String) {
        var isLoginSuccess = true
        loginRequest(name, passwd)
        if (RequestCode!=1) {
            isLoginSuccess = false
        }
        val result = isLoginSuccess
        handler.postDelayed({ iLoginView.onLoginResult(result, RequestCode) }, 5000)
    }

    override fun setProgressBarVisiblity(visiblity: Int) {
        iLoginView.onSetProgressBarVisibility(visiblity)
    }

    fun loginRequest(name: String, passwd: String){
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
                        RequestCode = -1
                    } catch (e: Exception) {
                        RequestCode = -1
                    }

                } else {
                    loginResponseModel = response.body()!!
                    RequestCode = 1

                    /*
                    val mPrefs = getActivity()!!.getPreferences(MODE_PRIVATE)
                    val prefsEditor = mPrefs.edit()
                    val gson = Gson()
                    val json = gson.toJson(currentuser) // myObject - instance of MyObject
                    prefsEditor.putString("MyObject", json)
                    prefsEditor.commit()
                */
                }

            override fun onFailure(call: Call<LoginResponseModel>, t: Throwable) {
                Log.e("login", "onFailure: ", t)
                RequestCode = 0
            }
        })
    }
}
