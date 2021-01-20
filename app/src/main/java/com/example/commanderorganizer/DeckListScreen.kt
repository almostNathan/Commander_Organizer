package com.example.commanderorganizer

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.FileInputStream
import java.io.ObjectInputStream

@Suppress("UNCHECKED_CAST")
class DeckListScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.deck_list_screen)

        val commanderKey = intent.extras?.getInt("Commander Key")

        val commanderHashMap = getCmdrHashMap()
        val commanderNameView = findViewById<TextView>(R.id.commander_name_deck_list_screen)
        commanderNameView.text = commanderHashMap[commanderKey]


        val deckListCards = ArrayList<Card>()
        //get our "DB" of cards
        val cardList : ArrayList<Card> = getCardList()

        //make list of cards in this deck
        for (item in cardList) {
            if (item.listOfCommanders.contains(commanderKey)) {
                deckListCards.add(item)
            }
        }


        val deckListRecyclerView = findViewById<RecyclerView>(R.id.deck_list_view)
        deckListRecyclerView.adapter = DeckListAdapter(deckListCards)
        deckListRecyclerView.layoutManager = LinearLayoutManager(this)

        if (deckListCards.size %2 == 0){
            deckListRecyclerView.setBackgroundColor(ContextCompat.getColor(deckListRecyclerView.context, R.color.card_background_1))
        }else{
            deckListRecyclerView.setBackgroundColor(ContextCompat.getColor(deckListRecyclerView.context, R.color.card_background_2))
        }


    }

    private fun getCmdrHashMap(): HashMap<Int, String> {
        val cmdrInputStream = ObjectInputStream(FileInputStream("${applicationContext.filesDir} cmdrHashMap"))
        val cmdrHashMap = cmdrInputStream.readObject() as HashMap<Int, String>
        cmdrInputStream.close()
        return cmdrHashMap
    }

    private fun getCardList(): ArrayList<Card> {
        val cardInputStream = ObjectInputStream(FileInputStream("${applicationContext.filesDir} cardsInDecks"))
        val cardsInDecks = cardInputStream.readObject() as ArrayList<Card>
        cardInputStream.close()
        return cardsInDecks
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
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
