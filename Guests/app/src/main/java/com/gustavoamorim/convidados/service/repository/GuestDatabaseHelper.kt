package com.gustavoamorim.convidados.service.repository

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.gustavoamorim.convidados.service.constants.DatabaseConstants

class GuestDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "Guest.db"

        private const val CREATE_TABLE_GUEST =
            ("CREATE TABLE ${DatabaseConstants.GUEST.TABLE_NAME} (" +
                    "${DatabaseConstants.GUEST.COLUMNS.ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "${DatabaseConstants.GUEST.COLUMNS.NAME} TEXT," +
                    "${DatabaseConstants.GUEST.COLUMNS.PRESENCE} INTEGER);")
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE_GUEST)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}

}