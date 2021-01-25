package com.example.commanderorganizer

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*

class AddDeckScreen : Activity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_deck_layout)

        val myIntent = intent

        val cmdrName = myIntent.extras?.getString("CMDR Name")

        val enterCard = findViewById<View>(R.id.add_card_auto_complete) as AutoCompleteTextView
        val deckList = findViewById<View>(R.id.add_deck_edit_text) as EditText

        deckList.append("*CMDR* $cmdrName")

        val listOfAllCards = getListOfAllCards()

        enterCard.setAdapter(ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listOfAllCards))

    }

    private fun getListOfAllCards(): MutableList<String> {
        val returnList = mutableListOf<String>()
        val inputStream = resources.openRawResource(R.raw.card_names)
        inputStream.bufferedReader().useLines { lines -> lines.forEach { returnList.add(it)} }

        inputStream.close()
        return returnList

    }

    fun addCardToDeck() {
        val deckList = findViewById<View>(R.id.add_deck_edit_text) as EditText
        val enterCard = findViewById<View>(R.id.add_card_auto_complete) as AutoCompleteTextView

        if(enterCard.text.toString() in getListOfAllCards()) {
            deckList.append("\n1x ${enterCard.text}")
        }
        enterCard.setText("")
    }


    fun submitDeck() {
        val deckListView = findViewById(R.id.add_deck_edit_text) as EditText
        val deckList = deckListView.text.toString()

        val returnToHomeScreen = Intent(applicationContext, HomeScreen::class.java)
        returnToHomeScreen.putExtra("DeckList String", deckList)
        setResult(RESULT_OK, returnToHomeScreen)
        finish()




    }
}