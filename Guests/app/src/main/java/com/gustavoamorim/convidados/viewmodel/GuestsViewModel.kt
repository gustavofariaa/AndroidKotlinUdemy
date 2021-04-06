package com.gustavoamorim.convidados.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gustavoamorim.convidados.service.constants.GuestConstants
import com.gustavoamorim.convidados.service.model.GuestModel
import com.gustavoamorim.convidados.service.repository.GuestRepository

class GuestsViewModel(application: Application) : AndroidViewModel(application) {

    private val mGuestRepository = GuestRepository(application.applicationContext)

    private val mGuestList = MutableLiveData<List<GuestModel>>()
    val guestList: LiveData<List<GuestModel>> = mGuestList

    fun load(filter: Int?) {
        when (filter) {
            GuestConstants.FILTER.ALL -> mGuestList.value = mGuestRepository.getAll()
            GuestConstants.FILTER.PRESENTS -> mGuestList.value = mGuestRepository.getPresents()
            GuestConstants.FILTER.ABSENTS -> mGuestList.value = mGuestRepository.getAbsents()
        }
    }

    fun delete(id: Int) {
        val guest = mGuestRepository.get(id)
        mGuestRepository.delete(guest)
    }
}