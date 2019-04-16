package com.alphagene.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.alphagene.R
import com.alphagene.presenter.IRegistrationPresenter
import com.alphagene.presenter.RegistrationPresenterCompl
import com.alphagene.view.interfaces.IRegistrationView
import kotlinx.android.synthetic.main.activity_registration.*

class RegistrationActivity : AppCompatActivity()  , IRegistrationView {

    private lateinit var registrationPresenter: IRegistrationPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        setupView()
    }

    private fun setupView() {
        btn_register.setOnClickListener {
            registrationPresenter.setProgressBarVisibility(View.VISIBLE)
            btn_register.isEnabled = false
            val firstName = input_first_name.text.toString()
            val lastName = input_last_name.text.toString()
            val phoneNumber = input_mobile.text.toString()
            val email = input_email.text.toString()
            val passwd = input_password.text.toString()

            if (firstName.isEmpty() || lastName.isEmpty() || phoneNumber.isEmpty() || email.isEmpty() || passwd.isEmpty()  ) {
                Toast.makeText(this, "Please Fill the needed data", Toast.LENGTH_SHORT).show()
                btn_register.isEnabled = true
                registrationPresenter.setProgressBarVisibility(View.INVISIBLE)
            } else
                registrationPresenter.doRegistration(firstName , lastName , email , passwd , phoneNumber)
        }

        //init
        registrationPresenter = RegistrationPresenterCompl(this)
        registrationPresenter.setProgressBarVisibility(View.INVISIBLE)

    }

    override fun onSetProgressBarVisibility(visibility: Int) {
        progress_register.visibility = visibility
    }


    override fun onRegistrationResult(result: Boolean, code: Int) {
        registrationPresenter.setProgressBarVisibility(View.INVISIBLE)
        if (result && code == 1) {
            Toast.makeText(this, "Registration Success", Toast.LENGTH_SHORT).show()
            //   goToHomeScreen()
        } else {
            Toast.makeText(this, "Registration Fail, code = $code", Toast.LENGTH_SHORT).show()
            btn_register.isEnabled = true
        }
    }

    private fun goToHomeScreen() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
