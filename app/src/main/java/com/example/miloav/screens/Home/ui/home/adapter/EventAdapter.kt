package com.example.miloav.screens.Home.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.miloav.R
import com.example.miloav.model.Event
import java.text.SimpleDateFormat
import java.util.Locale

interface EventAdapterClickListener {
    fun onItemClick(event: Event)
}

class EventAdapter(private val dataSet: MutableList<Event>, private val itemClickListener: EventAdapterClickListener) :
    RecyclerView.Adapter<EventAdapter.ViewHolder>() {


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val dataEvent: TextView
        val responsible: TextView

        init {
            dataEvent = view.findViewById(R.id.data_event)
            responsible = view.findViewById(R.id.responsible)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.event_row_item, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val event = dataSet[position]
        val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
        viewHolder.dataEvent.text = simpleDateFormat.format(event.eventDate?.seconds?.times(1000L) ?: 0)
        viewHolder.responsible.text = event.responsible?.name

        viewHolder.itemView.setOnClickListener {
            itemClickListener.onItemClick(event)
        }
    }

    override fun getItemCount() = dataSet.size

}