package dk.lgr.roombookingmvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dk.lgr.roombookingmvvm.UserInfo

class LoginViewModel: ViewModel() {

    private val isLoggedIn = MutableLiveData<Boolean>()
    val email:String =""
    val password:String = ""

    fun onLogin(){
        UserInfo.login(email)
        isLoggedIn.value = true
    }

    fun getIsLoggedIn():LiveData<Boolean> = isLoggedIn

}