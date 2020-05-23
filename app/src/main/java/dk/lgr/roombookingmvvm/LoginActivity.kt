package dk.lgr.roombookingmvvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import dk.lgr.roombookingmvvm.databinding.ActivityLoginBinding
import dk.lgr.roombookingmvvm.viewmodel.LoginViewModel

class LoginActivity : AppCompatActivity() {

    val viewModel:LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mBinding:ActivityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        mBinding.viewModel = viewModel
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel.getIsLoggedIn().observe(this, Observer {
            if(it){
                Toast.makeText(this, "You are logged in.", Toast.LENGTH_LONG).show()
                finish()
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
