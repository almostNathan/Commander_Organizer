package com.example.commanderorganizer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CardScreenCommanderAdapter (private val cardCmdrHashMap : HashMap<Int, String>) : RecyclerView.Adapter<CardScreenCommanderAdapter.ViewHolder>(){

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val textView = view.findViewById<TextView?>(R.id.commander_recycler_text_view)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardScreenCommanderAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_screen_commander_recycler_layout, parent, false)


        return ViewHolder(view)
    }



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        println(cardCmdrHashMap)
        val cmdrKeys = cardCmdrHashMap.keys.toIntArray()

        holder.textView?.text = cardCmdrHashMap[cmdrKeys[position]]
        holder.textView?.append(cmdrKeys[position].toString())

    }

    override fun getItemCount(): Int {
        return cardCmdrHashMap.size
    }


}