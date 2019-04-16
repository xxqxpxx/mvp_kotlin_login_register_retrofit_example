package com.alphagene.presenter

import android.util.Log
import com.alphagene.WebServices.Webservice
import com.alphagene.model.responseModels.LoginResponseModel
import com.alphagene.view.interfaces.IRegistrationView
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistrationPresenterCompl(private var iRegistrationView: IRegistrationView) : IRegistrationPresenter {


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
                    try {
                        //    val jObjError = JSONObject(response.errorBody()!!.string())
                        iRegistrationView.onRegistrationResult(false, -1)
                    } catch (e: Exception) {
                        iRegistrationView.onRegistrationResult(false, -1)
                    }
                } else {
                    iRegistrationView.onRegistrationResult(true, 1)

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
                iRegistrationView.onRegistrationResult(false, 0)
            }
        })
    }

    override fun setProgressBarVisibility(visibility: Int) {
        iRegistrationView.onSetProgressBarVisibility(visibility)
    }
}