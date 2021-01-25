package com.example.commanderorganizer

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class CommanderAdapter (private val commanderHashMap : HashMap<Int, String>) : RecyclerView.Adapter<CommanderAdapter.ViewHolder>() {

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val textView :TextView = view.findViewById(R.id.commander_name)

        init{
            textView.setOnClickListener {
                val theIntent = Intent(view.context, DeckListScreen::class.java)
                //check back when delete is implemented
                theIntent.putExtra("Commander Key", layoutPosition)

                view.context.startActivity(theIntent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.home_screen_commander_recycler_layout, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = commanderHashMap[position]
        holder.textView.append(position.toString())

        if(position % 2 == 1){
            holder.textView.setBackgroundColor(ContextCompat.getColor(holder.textView.context, R.color.commander_background_1))
        }else {
            holder.textView.setBackgroundColor(ContextCompat.getColor(holder.textView.context, R.color.commander_background_2))
        }
    }

    override fun getItemCount(): Int {
        return commanderHashMap.size
    }



}