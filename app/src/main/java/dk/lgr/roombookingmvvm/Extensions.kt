package dk.lgr.roombookingmvvm

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import java.util.*

fun Calendar.toDate() = SimpleDateFormat("dd/MM/yy", Locale.GERMAN).format(this.getTime())
fun Calendar.toDateTime() = SimpleDateFormat("dd/MM/yy - HH:mm", Locale.GERMAN).format(this.getTime())
fun Calendar.toUnix():Int = (this.time.time / 1000).toInt()
fun Int.toDateTimeString():String = SimpleDateFormat("dd/MM/yy HH:mm", Locale.GERMAN).format(this*1000L)