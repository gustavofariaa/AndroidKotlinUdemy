package com.gustavoamorim.mvvm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private var mRepository = PersonRepository()

    private var mTextTitle = MutableLiveData<String>()
    var textTitle = mTextTitle

    private var mLogin = MutableLiveData<Boolean>()
    var login = mLogin

    init {
        mTextTitle.value = "Hello World!"
    }

    fun login(login: String) {
        val response = mRepository.login(login)
        mLogin.value = response
    }
}