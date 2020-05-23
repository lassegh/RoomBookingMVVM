package dk.lgr.roombookingmvvm.repositotry

import dk.lgr.roombookingmvvm.model.Booking
import dk.lgr.roombookingmvvm.model.Room
import retrofit2.Call
import retrofit2.http.*

interface RemoteService {
    // get reservations from specific room
    @GET("reservations/room/{ids}/{fromTime}/{toTime}")
    fun getAllReservations(
        @Path("ids") ids: Int,
        @Path("fromTime") fromTime: Int,
        @Path("toTime") toTime: Int
    ): Call<List<Booking>>

    @POST("reservations")
    fun postReservation(@Body booking: Booking?): Call<Int>

    @GET("reservations/{id}")
    fun getOneReservation(id: Int): Call<Booking>

    @DELETE("reservations/{id}")
    fun deleteReservation(@Path("id") id: Int): Call<Unit>

    @GET("rooms")
    fun getAllRooms(): Call<List<Room>>

    @GET("rooms/free/{fromTime}")
    fun getAvailableRooms(@Path("fromTime") fromTime: Int): Call<List<Room>>
}