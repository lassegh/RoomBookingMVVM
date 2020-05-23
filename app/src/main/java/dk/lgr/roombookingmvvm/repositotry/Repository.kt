package dk.lgr.roombookingmvvm.repositotry

import android.icu.util.Calendar
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import dk.lgr.roombookingmvvm.UserInfo
import dk.lgr.roombookingmvvm.model.Booking
import dk.lgr.roombookingmvvm.toUnix
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Repository {
    companion object{
        var bookings = MutableLiveData<List<Booking>>()
        var exceptionMsg = MutableLiveData<String>()
        var isRefreshing = MutableLiveData<Boolean>()

        private const val BASE_URL = "http://anbo-roomreservationv3.azurewebsites.net/api/"

        private fun getBookingService(): RemoteService {
            return retrofit.create(RemoteService::class.java)
        }

        private val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        fun getBookings(roomNumber:Int, fromCal:Calendar?, toCal:Calendar?){
            if (toCal == null || fromCal == null) return
            isRefreshing.value = true
            val callReservations = getBookingService().getAllReservations(roomNumber, fromCal.toUnix(),toCal.toUnix())
            callReservations.enqueue(object: Callback<List<Booking>> {
                override fun onResponse(call: Call<List<Booking>>, response: Response<List<Booking>>) {
                    if (response.isSuccessful) {
                        bookings.value = response.body()!!.toList()
                    } else {
                        exceptionMsg.value = "'To date' cannot be earlier than 'from date'."
                    }
                    isRefreshing.value = false
                }
                override fun onFailure(call: Call<List<Booking>>, t: Throwable) {
                    exceptionMsg.value = "Error occurred. Try again later"
                    isRefreshing.value = false
                }
            })
        }

        fun deleteBooking(booking:Booking):Boolean{
            if (UserInfo.isLoggedIn.not()){
                exceptionMsg.value = "Please log in to delete booking."
                return false
            }

            if (UserInfo.email != booking.userId){
                exceptionMsg.value = "You can only delete your own bookings."
                return false
            }

            val callDelete = getBookingService().deleteReservation(booking.id)
            callDelete.enqueue(object : Callback<Unit>{
                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                    if (response.isSuccessful)exceptionMsg.value = "Booking deleted."
                    else exceptionMsg.value = "An error occurred: 4xx"
                }

                override fun onFailure(call: Call<Unit>, t: Throwable) {
                    exceptionMsg.value = "An error occurred: 5xx"
                }
            })
            return true
        }
    }
}