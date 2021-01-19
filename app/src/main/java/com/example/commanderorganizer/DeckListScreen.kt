package com.example.commanderorganizer

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text
import java.io.FileInputStream
import java.io.ObjectInputStream

class DeckListScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.deck_list_screen)

        val commanderKey = intent.extras?.getInt("Commander Key")

        val commanderHashMap = getCmdrHashMap()
        val commanderNameView = findViewById<TextView>(R.id.commander_name_deck_list_screen)
        commanderNameView.text = commanderHashMap[commanderKey]


        val deckListCards = ArrayList<Card>()

        val cardList = getCardList()

        for (item in cardList){

            if (item.listOfCommanders.contains(commanderKey)){

                deckListCards.add(item)
            }
        }
        val deckListView = findViewById<RecyclerView>(R.id.deck_list_view)
        deckListView.adapter = DeckListAdapter(deckListCards)
        deckListView.layoutManager = LinearLayoutManager(this)




    }

    private fun getCmdrHashMap(): HashMap<Int, String> {
        val cmdrInputStream = ObjectInputStream(FileInputStream("${applicationContext.filesDir} cmdrHashMap"))
        val cmdrHashMap = cmdrInputStream.readObject() as HashMap<Int, String>
        cmdrInputStream.close()
        return cmdrHashMap
    }

    private fun getCardList(): ArrayList<Card> {
        val cardsInDecks : ArrayList<Card>
        val cardInputStream = ObjectInputStream(FileInputStream("${applicationContext.filesDir} cardsInDecks"))
        cardsInDecks = cardInputStream.readObject() as ArrayList<Card>
        cardInputStream.close()
        return cardsInDecks
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true;
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.add_deck) {
            val intent = Intent(this, HomeScreen::class.java)
            finish()
            startActivity(intent)
        }
        return true
    }


}