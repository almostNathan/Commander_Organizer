package com.example.commanderorganizer

import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class DeckListAdapter (private val cardList : ArrayList<Card>) : RecyclerView.Adapter<DeckListAdapter.ViewHolder>() {




    class ViewHolder(val view : View) : RecyclerView.ViewHolder(view){
        val textView : TextView = view.findViewById(R.id.card_name)


    }

    override fun getItemCount(): Int {
        return cardList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = cardList[position].cardName
        holder.textView.append(cardList[position].listOfCommanders.toString())

        if (position % 2 == 1){
            holder.textView.setBackgroundColor(ContextCompat.getColor(holder.textView.context, R.color.card_background_1))
        }else{
            holder.textView.setBackgroundColor(ContextCompat.getColor(holder.textView.context, R.color.card_background_2))
        }

        holder.textView.setOnClickListener {
            val theIntent = Intent(holder.view.context, CardScreen::class.java)
            theIntent.putExtra("Card", cardList[position])

            holder.view.context.startActivity(theIntent)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.deck_list_card_layout, parent, false)


        return ViewHolder(view)
    }

}