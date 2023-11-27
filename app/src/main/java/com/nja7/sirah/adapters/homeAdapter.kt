package com.nja7.sirah.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nja7.sirah.R
import com.nja7.sirah.model.Person
import java.util.Locale

class homeAdapter(private val nameList: List<Person>) : RecyclerView.Adapter<homeAdapter.ViewHolder>() {
    private var filteredList: List<Person> = nameList

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textName: TextView = itemView.findViewById(R.id.name)
        val phoneNumber: TextView = itemView.findViewById(R.id.phoneNumber)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.model, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val nameModel = filteredList[position]

        holder.textName.text = nameModel.name
        holder.phoneNumber.text = nameModel.number.toString()
    }

    override fun getItemCount(): Int {
        return filteredList.size
    }

    fun filter(query: String) {
        val lowerCaseQuery = query.toLowerCase(Locale.getDefault())
        filteredList = nameList.filter { it.name?.toLowerCase(Locale.getDefault())!!.contains(lowerCaseQuery) }
        notifyDataSetChanged()
    }

}
