package com.example.commanderorganizer

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.contentValuesOf
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

class CommanderAdapter (private val commanderHashMap : HashMap<Int, String>) : RecyclerView.Adapter<CommanderAdapter.ViewHolder>() {




    class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val textView :TextView = view.findViewById(R.id.commander_name)

        init{
            textView.setOnClickListener {
                val theIntent = Intent(view.context, DeckListScreen::class.java)
                theIntent.putExtra("Commander Key", layoutPosition)

                view.context.startActivity(theIntent)
            }
        }
    }

    override fun getItemCount(): Int {
        return commanderHashMap.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = commanderHashMap[position]
        holder.textView.append(position.toString())
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.commander_adapter_layout, parent, false)

        return ViewHolder(view)
    }

}