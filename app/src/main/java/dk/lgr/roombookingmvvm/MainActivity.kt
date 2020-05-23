package dk.lgr.roombookingmvvm

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.drawable.Drawable
import android.icu.util.Calendar
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.get
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import dk.lgr.roombookingmvvm.view.adapter.BookingAdapter
import dk.lgr.roombookingmvvm.view.adapter.SwipeToDeleteCallback
import dk.lgr.roombookingmvvm.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.toolbar
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        // set recyclerView
        mainRecyclerView.layoutManager = LinearLayoutManager(this)
        mainRecyclerView.adapter = BookingAdapter(this, listOf())
        val itemTouchHelper = ItemTouchHelper(SwipeToDeleteCallback(mainRecyclerView.adapter as BookingAdapter))
        itemTouchHelper.attachToRecyclerView(mainRecyclerView)

        // Listeners
        mainInputFromDateEditText.setOnClickListener { showDatePicker(viewModel.getFromCalendar().value!!, "from")  }
        mainInputToDateEditText.setOnClickListener { showDatePicker(viewModel.getToCalendar().value!!, "to") }
        mainSwiperefresh.setOnRefreshListener { viewModel.updateBookings() }

        // Observers
        viewModel.getFromCalendar().observe(this, Observer { mainInputFromDateEditText.setText(it.toDate()) })
        viewModel.getToCalendar().observe(this, Observer { mainInputToDateEditText.setText(it.toDate()) })
        viewModel.getIsUpdatingBookings().observe(this, Observer { mainSwiperefresh.isRefreshing = it })
        viewModel.getBookings().observe(this, Observer { (mainRecyclerView.adapter as BookingAdapter).updateContent(it) })
        viewModel.getExceptionMsg().observe(this, Observer { showToast(it) })

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        viewModel.updateBookings()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_select_room -> true
            R.id.action_log_in -> {
                loginHandler()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun loginHandler(){
        if (UserInfo.isLoggedIn){
            // Show alertbox
        }
        else{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent, null)
        }
    }

    fun showDatePicker(cal:Calendar, switch:String){
        DatePickerDialog(this, viewModel.setCal(
            cal, switch), cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show()
    }

    fun showToast(msg:String){
        Toast.makeText(this,msg, Toast.LENGTH_LONG).show()
    }

}
