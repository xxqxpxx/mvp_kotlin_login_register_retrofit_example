package com.alphagene.presenter.implemenation

import android.util.Log
import com.alphagene.WebServices.Webservice
import com.alphagene.model.responseModels.LoginResponseModel
import com.alphagene.presenter.interfaces.IForgetPasswordPresenter
import com.alphagene.view.interfaces.IForgetPasswordView
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForgetPasswordPresenterImpl(var iforgetPasswordView: IForgetPasswordView) :
    IForgetPasswordPresenter {

    lateinit var identificationKey: String

    override fun doForgetPassword(name: String) {
        val data = HashMap<String, String>()
        data["identity"] = name

        val body = RequestBody.create(
            okhttp3.MediaType.parse("application/json; charset=utf-8"),
            JSONObject(data).toString()
        )

        Webservice.getInstance().getApi().forgot_password(body).enqueue(object : Callback<LoginResponseModel> {
            override fun onResponse(call: Call<LoginResponseModel>, response: Response<LoginResponseModel>) =
                if (!response.isSuccessful) {
                    try {
                        //    val jObjError = JSONObject(response.errorBody()!!.string())
                        iforgetPasswordView.onForgetPasswordResult(false, -1)
                    } catch (e: Exception) {
                        iforgetPasswordView.onForgetPasswordResult(false, -1)
                    }
                } else {
                    //   loginResponseModel = response.body()!!
                    iforgetPasswordView.onForgetPasswordResult(true, 1)

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
                iforgetPasswordView.onForgetPasswordResult(false, 0)
            }
        })

    }

    override fun doVerificationCode(name: String) {
        val data = HashMap<String, String>()
        data["identificationKey"] = name
        data["authenticationCode"] = name

        val body = RequestBody.create(
            okhttp3.MediaType.parse("application/json; charset=utf-8"),
            JSONObject(data).toString()
        )

        Webservice.getInstance().getApi().verify_authentication_code(body)
            .enqueue(object : Callback<LoginResponseModel> {
                override fun onResponse(call: Call<LoginResponseModel>, response: Response<LoginResponseModel>) =
                    if (!response.isSuccessful) {
                        try {
                            //    val jObjError = JSONObject(response.errorBody()!!.string())
                            iforgetPasswordView.onVerificationCodeResult(false, -1)
                        } catch (e: Exception) {
                            iforgetPasswordView.onVerificationCodeResult(false, -1)
                        }
                    } else {
                        //   loginResponseModel = response.body()!!
                        iforgetPasswordView.onVerificationCodeResult(true, 1)

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
                    iforgetPasswordView.onVerificationCodeResult(false, 0)
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
