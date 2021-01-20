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
class CardScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.card_screen_layout)

        val passedCard = intent.extras?.get("Card") as Card
        val commanderHashMap = getCmdrHashMap()
        val cardCmdrHashMap = HashMap<Int, String>()


        for (item in commanderHashMap){
            if (item.key in passedCard.listOfCommanders){
                cardCmdrHashMap.put(item.key, item.value)
            }
        }

        //get views
        val cardScreenCardName = findViewById<TextView>(R.id.card_screen_card_name)

        cardScreenCardName.text = passedCard.cardName


        val commanderNameRecyclerView = findViewById<RecyclerView>(R.id.card_screen_commander_list)
        commanderNameRecyclerView.adapter = CardScreenCommanderAdapter(cardCmdrHashMap)
        commanderNameRecyclerView.layoutManager = LinearLayoutManager(this)

        if (cardCmdrHashMap.size %2 == 0){
            commanderNameRecyclerView.setBackgroundColor(ContextCompat.getColor(commanderNameRecyclerView.context, R.color.card_background_1))
        }else{
            commanderNameRecyclerView.setBackgroundColor(ContextCompat.getColor(commanderNameRecyclerView.context, R.color.card_background_2))
        }





    }

    private fun getCmdrHashMap(): HashMap<Int, String> {
        val cmdrInputStream = ObjectInputStream(FileInputStream("${applicationContext.filesDir} cmdrHashMap"))
        val cmdrHashMap = cmdrInputStream.readObject() as HashMap<Int, String>
        cmdrInputStream.close()
        return cmdrHashMap
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