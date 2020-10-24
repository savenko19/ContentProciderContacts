package com.example.content

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ContactsAdapter(private val contacts: List<Contact>):
    RecyclerView.Adapter<ContactsAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.contact_item_layout, parent, false))
    }

    override fun onBindViewHolder(holder: ContactsAdapter.ViewHolder, position: Int) {
        holder.bind(contacts[position])
    }

    override fun getItemCount(): Int = contacts.size

    inner class ViewHolder(itemView : View): RecyclerView.ViewHolder(itemView){
        private val name = itemView.findViewById<TextView>(R.id.name)
        private val number = itemView.findViewById<TextView>(R.id.number)

        fun bind(contact: Contact) {
            name.text = contact.name
            number.text = contact.number
        }
    }
}