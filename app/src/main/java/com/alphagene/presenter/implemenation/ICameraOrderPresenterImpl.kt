package com.alphagene.presenter.implemenation

import android.util.Log
import com.alphagene.webServices.Webservice
import com.alphagene.presenter.interfaces.ICameraOrderPresenter
import com.alphagene.view.interfaces.ICameraOrderView
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ICameraOrderPresenterImpl(var iCameraOrderPresenterView: ICameraOrderView) : ICameraOrderPresenter {

    override fun doCameraOrder(userID: String, sessionID: String, prescriptionImage: String) {
        val data = HashMap<String, String>()
        data["userId"] = userID
        data["sessionId"] = sessionID
        data["prescriptionImage"] = prescriptionImage

        val body = RequestBody.create(
            okhttp3.MediaType.parse("application/json; charset=utf-8"),
            JSONObject(data).toString()
        )

        Webservice.getInstance().getApi().submitOrder(body).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) =
                if (!response.isSuccessful) {
                    iCameraOrderPresenterView.onCameraOrderResult(false, -1)
                } else {
                  //  loginResponseModel = response.body()!!
                    iCameraOrderPresenterView.onCameraOrderResult(true, 1)
                }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("login", "onFailure: ", t)
                iCameraOrderPresenterView.onCameraOrderResult(false, 0)
            }
        })

        }

    override fun setProgressBarVisibility(visibility: Int) {
        iCameraOrderPresenterView.onSetProgressBarVisibility(visibility)
    }
}