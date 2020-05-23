package dk.lgr.roombookingmvvm.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import dk.lgr.roombookingmvvm.R
import dk.lgr.roombookingmvvm.databinding.BookingRowBinding
import dk.lgr.roombookingmvvm.model.Booking
import dk.lgr.roombookingmvvm.repositotry.Repository

class BookingAdapter(private val context: Context, bookings:List<Booking>) : RecyclerView.Adapter<BookingAdapter.BindingViewHolder>() {

    private var bookings:MutableList<Booking> = bookings.toMutableList()

    class BindingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: BookingRowBinding? = DataBindingUtil.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder {
        val binding:BookingRowBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.booking_row, parent, false)

        return BindingViewHolder(binding.root)
    }

    override fun getItemCount(): Int = bookings.count()

    override fun onBindViewHolder(holder: BindingViewHolder, position: Int) {
        val booking = bookings.get(position)
        holder.binding?.booking = booking
    }

    fun updateContent(bookings: List<Booking>){
        this.bookings.clear()
        this.bookings.addAll(bookings.toMutableList())
        notifyDataSetChanged()
    }

    fun deleteItem(position: Int){
        if (Repository.deleteBooking(bookings[position])){
            bookings.removeAt(position)
        }
        notifyDataSetChanged()
    }
}