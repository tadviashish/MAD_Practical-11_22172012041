package com.example.mad_practical_11_22172012041

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class PersonAdapter(context:Context,val array:ArrayList<Person>):RecyclerView.Adapter<PersonAdapter.PersonViewHolder>() {
    inner class PersonViewHolder(val view:View):ViewHolder(view)
    interface OnItemDeleteListener {
        fun onItemDeleted(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.person_item,parent,false)
        return PersonViewHolder(view)
    }

    override fun getItemCount(): Int {
        return array.size
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        val persondata = array[position]

        holder.view.findViewById<TextView>(R.id.name).text = persondata.name
        holder.view.findViewById<TextView>(R.id.mobile).text = persondata.phoneno
        holder.view.findViewById<TextView>(R.id.email).text = persondata.emailid
        holder.view.findViewById<TextView>(R.id.addr).text = persondata.address
        holder.view.findViewById<Button>(R.id.btn_location).setOnClickListener{
            val intent = Intent(holder.view.context, MapsActivity::class.java)
            intent.putExtra("Object", persondata)
            holder.view.context.startActivity(intent)
        }
        holder.view.findViewById<Button>(R.id.btn_delete).setOnClickListener{
            val position = holder.adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                array.removeAt(position)
                notifyItemRemoved(position)
            }
        }
    }

}