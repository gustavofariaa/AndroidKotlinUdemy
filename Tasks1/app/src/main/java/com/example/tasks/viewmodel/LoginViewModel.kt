package com.example.tasks.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tasks.service.repository.model.HeaderModel
import com.example.tasks.service.constants.TaskConstants
import com.example.tasks.service.helper.FingerprintHelper
import com.example.tasks.service.listener.APIListener
import com.example.tasks.service.listener.ValidationListener
import com.example.tasks.service.repository.PersonRepository
import com.example.tasks.service.repository.PriorityRepository
import com.example.tasks.service.repository.local.SecurityPreferences
import com.example.tasks.service.repository.remote.RetrofitClient

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val mPersonRepository = PersonRepository(application)
    private val mPriorityRepository = PriorityRepository(application)
    private val mSharedPreferences = SecurityPreferences(application)

    private val mLogin = MutableLiveData<ValidationListener>()
    var login: LiveData<ValidationListener> = mLogin

    private val mFingerprint = MutableLiveData<Boolean>()
    var fingerprint: LiveData<Boolean> = mFingerprint

    /**
     * Faz login usando API
     */
    fun doLogin(email: String, password: String) {
        mPersonRepository.login(email, password, object : APIListener<HeaderModel> {

            override fun onSuccess(model: HeaderModel) {
                mSharedPreferences.store(TaskConstants.SHARED.TOKEN_KEY, model.token)
                mSharedPreferences.store(TaskConstants.SHARED.PERSON_KEY, model.personKey)
                mSharedPreferences.store(TaskConstants.SHARED.PERSON_NAME, model.name)

                RetrofitClient.addHeader(model.personKey, model.token)

                mLogin.value = ValidationListener()
            }

            override fun onFailure(message: String) {
                mLogin.value = ValidationListener(message)
            }

        })
    }

    fun isAuthenticationAvaliable() {
        val personKey = mSharedPreferences.get(TaskConstants.SHARED.PERSON_KEY)
        val token = mSharedPreferences.get(TaskConstants.SHARED.TOKEN_KEY)
        val everLogged = (personKey != "" && token != "")

        RetrofitClient.addHeader(personKey, token)

        if (!everLogged) {
            mPriorityRepository.all()
        }

        if (FingerprintHelper.isAuthenticationAvaliable(getApplication())) {
            mFingerprint.value = everLogged
        }
    }

}