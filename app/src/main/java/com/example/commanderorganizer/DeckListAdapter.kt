package com.example.commanderorganizer

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DeckListAdapter (private val cardList : ArrayList<Card>) : RecyclerView.Adapter<DeckListAdapter.ViewHolder>() {




    class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val textView : TextView = view.findViewById(R.id.card_name)


    }

    override fun getItemCount(): Int {
        return cardList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = cardList[position].cardName
        holder.textView.append(cardList[position].listOfCommanders.toString())
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.deck_list_card_layout, parent, false)

        return ViewHolder(view)
    }

}