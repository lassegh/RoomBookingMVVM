package dk.lgr.roombookingmvvm

class UserInfo {
    companion object{
        var email:String = ""
        var isLoggedIn = false

        fun login(email:String):Boolean{
            this.email = email
            isLoggedIn = true
            return true
        }

        fun logout(){
            this.email = ""
            isLoggedIn = false
        }
    }
}