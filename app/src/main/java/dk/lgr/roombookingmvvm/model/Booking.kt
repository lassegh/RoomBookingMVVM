package dk.lgr.roombookingmvvm.model

data class Booking(val id:Int, val fromTime:Int, val toTime:Int, val userId:String, val purpose:String, val roomId:Int)