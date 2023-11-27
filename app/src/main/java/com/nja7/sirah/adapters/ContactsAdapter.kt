package com.nja7.sirah.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nja7.sirah.R
import com.nja7.sirah.model.Person
import java.util.Locale

class ContactsAdapter(private val contacts: List<Person>) :
    RecyclerView.Adapter<ContactsAdapter.ViewHolder>() {
    private var filteredList: List<Person> = contacts

    private val selectedContacts = mutableListOf<Person>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val checkBox: CheckBox = itemView.findViewById(R.id.model_checked_check)
        val nameTextView: TextView = itemView.findViewById( R.id.nameCheck)
        val numberTextView: TextView = itemView.findViewById(R.id.phoneNumberCheck)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.modelcheckd, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val contact = filteredList[position]

        holder.nameTextView.text = contact.name
        holder.numberTextView.text = contact.number
        // Set checkbox state and handle its click
        holder.checkBox.isChecked = selectedContacts.contains(contact)
        holder.checkBox.setOnClickListener {
            if (holder.checkBox.isChecked) {
                selectedContacts.add(contact)
            } else {
                selectedContacts.remove(contact)
            }
        }
    }

    override fun getItemCount(): Int {
        return filteredList.size
    }

    // Get the list of selected contacts
    fun getSelectedContacts(): List<Person> {
        return selectedContacts
    }

    fun filter(query: String) {
        val lowerCaseQuery = query.toLowerCase(Locale.getDefault())
        filteredList = contacts.filter { it.name?.toLowerCase(Locale.getDefault())!!.contains(lowerCaseQuery) }
        notifyDataSetChanged()
    }
}
