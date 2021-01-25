package com.example.commanderorganizer

import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
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

        //create gradient for commander name
        commanderNameView.background = CreateGradient(applicationContext, commanderHashMap[commanderKey]).getBackgroundGradient()

        //set up each array to hold cards for each category
        val oneOtherDeckListOfCards = ArrayList<Card>()
        val multipleOtherDecksListOfCards = ArrayList<Card>()
        val thisDeckOnlyListOfCards = ArrayList<Card>()

        //get our "DB" of cards
        val cardList : ArrayList<Card> = getCardList()

        //make list of cards in this deck
        for (item in cardList) {
            if (item.listOfCommanders.contains(commanderKey) && item.listOfCommanders.size == 1) {
                thisDeckOnlyListOfCards.add(item)
            } else if (item.listOfCommanders.contains(commanderKey) && item.listOfCommanders.size == 2) {
                oneOtherDeckListOfCards.add(item)
            }else if (item.listOfCommanders.contains(commanderKey)){
                multipleOtherDecksListOfCards.add(item)
            }
        }

        //one other deck adapter set up
        val oneOtherDeckRecyclerView = findViewById<RecyclerView>(R.id.cards_in_one_other_deck)
        oneOtherDeckRecyclerView.adapter = DeckListCardAdapter(oneOtherDeckListOfCards)
        oneOtherDeckRecyclerView.layoutManager = LinearLayoutManager(this)


        //multiple other decks adapther set up
        val multipleOtherDecksRecyclerView = findViewById<RecyclerView>(R.id.cards_in_multiple_other_decks)
        multipleOtherDecksRecyclerView.adapter = DeckListCardAdapter(multipleOtherDecksListOfCards)
        multipleOtherDecksRecyclerView.layoutManager = LinearLayoutManager(this)


        //this deck only adapter set up
        val thisDeckOnlyRecyclerView = findViewById<RecyclerView>(R.id.cards_in_this_deck_only)
        thisDeckOnlyRecyclerView.adapter = DeckListCardAdapter(thisDeckOnlyListOfCards)
        thisDeckOnlyRecyclerView.layoutManager = LinearLayoutManager(this)


        //turn off titles for empty recyclerViews
        if(oneOtherDeckListOfCards.size == 0) findViewById<TextView>(R.id.one_other_deck_title).visibility = View.GONE
        if(multipleOtherDecksListOfCards.size == 0) findViewById<TextView>(R.id.multiple_other_decks_title).visibility = View.GONE



        if (thisDeckOnlyListOfCards.size %2 == 0){
            val theView = this.findViewById<View>(android.R.id.content)
            theView.setBackgroundColor(ContextCompat.getColor(theView.context, R.color.card_background_1))
        }else{
            val theView = this.findViewById<View>(android.R.id.content)
            theView.setBackgroundColor(ContextCompat.getColor(theView.context, R.color.card_background_2))
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
