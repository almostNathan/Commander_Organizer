package com.example.commanderorganizer

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import java.io.File
import java.io.FileOutputStream
import java.io.ObjectOutputStream

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpBaseDeck()


    }

    private fun setUpBaseDeck() {
        val listOfCards = ArrayList<Card>()
        listOfCards.add(Card("Forest", 1))
        val starterCommanders = HashMap<Int, String>(16)

        val file = File("${applicationContext.filesDir} cardsInDecks")
        if (!file.exists()) {
            file.createNewFile()
            val newOutputStream = ObjectOutputStream(FileOutputStream(file))
            newOutputStream.writeObject(listOfCards)
            newOutputStream.close()
        }
        val newFile = File("${applicationContext.filesDir} cmdrHashMap")
        if (!newFile.exists()) {
            newFile.createNewFile()
            val outputStream = ObjectOutputStream(FileOutputStream(newFile))
            outputStream.writeObject(starterCommanders)
            outputStream.close()

        }




    }

    fun startApp(view: View) {
        val startApp= Intent(this, HomeScreen::class.java)
        startActivity(startApp)
        finish()
    }


}