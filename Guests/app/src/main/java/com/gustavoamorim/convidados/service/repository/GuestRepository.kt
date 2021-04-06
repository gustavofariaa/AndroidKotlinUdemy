package com.gustavoamorim.convidados.service.repository

import android.content.Context
import com.gustavoamorim.convidados.service.model.GuestModel

class GuestRepository constructor(context: Context) {

    private val mDatabase = GuestDatabase.getDatabase(context).guestDAO()

    fun get(id: Int): GuestModel {
        return mDatabase.get(id)
    }

    fun getAll(): List<GuestModel> {
        return mDatabase.getAll()
    }

    fun getPresents(): List<GuestModel> {
        return mDatabase.getPresents()
    }

    fun getAbsents(): List<GuestModel> {
        return mDatabase.getAbsents()
    }

    fun save(guest: GuestModel): Boolean {
        return mDatabase.save(guest) > 0
    }

    fun update(guest: GuestModel): Boolean {
        return mDatabase.update(guest) > 0
    }

    fun delete(guest: GuestModel) {
        mDatabase.update(guest)
    }


}