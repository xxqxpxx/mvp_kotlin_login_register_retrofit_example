package com.alphagene.presenter.implemenation

import android.util.Log
import com.alphagene.WebServices.Webservice
import com.alphagene.model.responseModels.LoginResponseModel
import com.alphagene.presenter.interfaces.IUpdatePasswordPresenter
import com.alphagene.view.interfaces.IUpdatePasswordView
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
        data["newPassword"] = password

        val body = RequestBody.create(
            okhttp3.MediaType.parse("application/json; charset=utf-8"),
            JSONObject(data).toString()
        )

        Webservice.getInstance().getApi().reset_password(body).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) =
                if (!response.isSuccessful) {
                    try {
                        //    val jObjError = JSONObject(response.errorBody()!!.string())
                        iUpdatePasswordView.onUpdatePasswordResult(false, -1)
                    } catch (e: Exception) {
                        iUpdatePasswordView.onUpdatePasswordResult(false, -1)
                    }
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