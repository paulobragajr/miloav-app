package com.example.miloav.screens.Home.ui.members.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.miloav.R
import com.example.miloav.model.Member

class MemberAdapter(private val dataSet: MutableList<Member>) :
    RecyclerView.Adapter<MemberAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView
        val descriptionFunction: TextView

        init {
            name = view.findViewById(R.id.name)
            descriptionFunction = view.findViewById(R.id.description_function)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.member_row_item, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val member = dataSet[position]
        viewHolder.name.text = member.name
        viewHolder.descriptionFunction.text = member.descriptionFunction.lowercase()
    }

    override fun getItemCount() = dataSet.size

}