package com.mvp_kotlin_login_register_retrofit_example.presenter.implemenation

import android.util.Log
import com.mvp_kotlin_login_register_retrofit_example.webServices.Webservice
import com.mvp_kotlin_login_register_retrofit_example.presenter.interfaces.IUpdatePasswordPresenter
import com.mvp_kotlin_login_register_retrofit_example.view.interfaces.IUpdatePasswordView
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdatePasswordPresenterImpl (var iUpdatePasswordView: IUpdatePasswordView)  : IUpdatePasswordPresenter {

    override fun doUpdatePassword(password: String , authenticationToken: String  ) {
        val data = HashMap<String, String>()
        data["authenticationToken"] = authenticationToken
        data["password"] = password

        val body = RequestBody.create(
            okhttp3.MediaType.parse("application/json; charset=utf-8"),
            JSONObject(data).toString()
        )

        Webservice.getInstance().getApi().reset_password(body).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) =
                if (!response.isSuccessful) {
                    iUpdatePasswordView.onUpdatePasswordResult(false, -1)
                } else {
                    iUpdatePasswordView.onUpdatePasswordResult(true, 1)
                }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("login", "onFailure: ", t)
                iUpdatePasswordView.onUpdatePasswordResult(false, 0)
            }
        })    }

    override fun setProgressBarVisibility(visibility: Int) {
        iUpdatePasswordView.onSetProgressBarVisibility(visibility)
    }
}