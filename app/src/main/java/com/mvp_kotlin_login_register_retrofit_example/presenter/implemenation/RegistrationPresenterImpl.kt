package com.mvp_kotlin_login_register_retrofit_example.presenter.implemenation

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.util.Log
import com.mvp_kotlin_login_register_retrofit_example.model.responseModels.LoginResponseModel
import com.mvp_kotlin_login_register_retrofit_example.webServices.Webservice
import com.mvp_kotlin_login_register_retrofit_example.presenter.interfaces.IRegistrationPresenter
import com.mvp_kotlin_login_register_retrofit_example.view.interfaces.IRegistrationView
import com.google.gson.Gson
import okhttp3.RequestBody
import org.json.JSONObject

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistrationPresenterImpl(private var iRegistrationView: IRegistrationView , val context: Context) : IRegistrationPresenter {
    lateinit var registerationResponseModel: LoginResponseModel
    private val mPrefs: SharedPreferences by lazy { PreferenceManager.getDefaultSharedPreferences(context) }

    override fun doRegistration(
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        mobile: String,
        city: String,
        street: String,
        building: String,
        floor: String,
        apartment: String
    ) {
        val data = HashMap<String, String>()
        data["firstName"] = firstName
        data["lastName"] = lastName
        data["phoneNumber"] = mobile
        data["email"] = email
        data["password"] = password
        data["city"] = city
        data["street"] = street
        data["building"] = building
        data["floor"] = floor
        data["apartment"] = apartment

        val body = RequestBody.create(
            okhttp3.MediaType.parse("application/json; charset=utf-8"),
            JSONObject(data).toString()
        )

        Webservice.getInstance().getApi().register(body).enqueue(object : Callback<LoginResponseModel> {
            override fun onResponse(call: Call<LoginResponseModel>, response: Response<LoginResponseModel>) =
                if (!response.isSuccessful) {
                    iRegistrationView.onRegistrationResult(false, -1 , "")
                    //    val jObjError = JSONObject(response.errorBody()!!.string())
                } else {
                    registerationResponseModel = response.body()!!

                    val prefsEditor = mPrefs.edit()
                    val gson = Gson()
                    val json = gson.toJson( registerationResponseModel )
                    prefsEditor.putString("MyObject", json)
                    prefsEditor.putBoolean("isLogin" , true)
                    prefsEditor.apply()

                    iRegistrationView.onRegistrationResult(true, 1 , "")
                }

            override fun onFailure(call: Call<LoginResponseModel>, t: Throwable) {
                Log.e("login", "onFailure: ", t)
                iRegistrationView.onRegistrationResult(false, 0 , "")
            }
        })
    }

    override fun setProgressBarVisibility(visibility: Int) {
        iRegistrationView.onSetProgressBarVisibility(visibility)
    }
}