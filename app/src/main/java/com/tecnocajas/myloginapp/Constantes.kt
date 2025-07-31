package com.tecnocajas.myloginapp

import android.icu.util.Calendar
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.zip.DataFormatException

object Constantes {
    fun obtenerTiempoUid() : Long{
        return System.currentTimeMillis()
    }
    fun obtenerFecha(tiempo: Long): String {
        val calendario = Calendar.getInstance(Locale.ENGLISH)
        calendario.timeInMillis = tiempo

        val formato = SimpleDateFormat("dd/MM/yy", Locale.ENGLISH)
        return formato.format(calendario.time)
    }
}