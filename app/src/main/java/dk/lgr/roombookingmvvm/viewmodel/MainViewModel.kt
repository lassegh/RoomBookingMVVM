package dk.lgr.roombookingmvvm.viewmodel

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.widget.DatePicker
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import dk.lgr.roombookingmvvm.model.Booking
import dk.lgr.roombookingmvvm.repositotry.Repository

class MainViewModel : ViewModel() {

    private var fromCalendar = MutableLiveData<Calendar>()
    private var toCalendar = MutableLiveData<Calendar>()
    private var bookings = MutableLiveData<List<Booking>>()
    private var isUpdatingBookings = MutableLiveData<Boolean>()
    private var exceptionMsg = MutableLiveData<String>()

    init {
        fromCalendar.value = Calendar.getInstance()
        toCalendar.value = Calendar.getInstance()
        toCalendar.run { value!!.add(Calendar.DAY_OF_MONTH,7) }
        Repository.bookings.observeForever(Observer { bookings.value = it })
        Repository.exceptionMsg.observeForever(Observer { exceptionMsg.value = it })
        Repository.isRefreshing.observeForever(Observer { isUpdatingBookings.value = it })
    }

    fun getFromCalendar():LiveData<Calendar> = fromCalendar
    fun getToCalendar():LiveData<Calendar> = toCalendar
    fun getBookings():LiveData<List<Booking>> = bookings
    fun getIsUpdatingBookings():LiveData<Boolean> = isUpdatingBookings
    fun getExceptionMsg():LiveData<String> = exceptionMsg

    fun updateBookings(){
            Repository.getBookings(1, fromCalendar.value, toCalendar.value)
    }

    fun setCal(cal: Calendar, switch:String): DatePickerDialog.OnDateSetListener? {
        return DatePickerDialog.OnDateSetListener { _: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int ->
            cal[Calendar.YEAR] = year
            cal[Calendar.MONTH] = monthOfYear
            cal[Calendar.DAY_OF_MONTH] = dayOfMonth

            when (switch){
                "from"->fromCalendar.value = cal
                "to"->toCalendar.value = cal
            }

            updateBookings()
        }
    }
}