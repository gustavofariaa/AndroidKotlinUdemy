package com.gustavoamorim.convidados.service.constants

class GuestConstants private constructor(){

    companion object {
        const val ID = "guestId"
    }

    object FILTER {
        const val ABSENTS = 0
        const val PRESENTS = 1
        const val ALL = 2
    }

}
