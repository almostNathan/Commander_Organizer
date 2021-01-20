package com.example.commanderorganizer

import android.app.Activity
import android.content.Intent
import android.content.Intent.getIntent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

@Suppress("UNCHECKED_CAST")
class HomeScreen : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_screen_layout)
        drawCmdrList()
    }


    private fun addDeckToLibrary(deckListString: String?) {
        val deckListArray = deckListString?.split("\n")
        val commanderName = findCommander(deckListArray)
        val commanderIndex = addCommander(commanderName)


        if(deckListArray != null){
            for(item in deckListArray){
                val newItem = item.split(" ", ignoreCase = false, limit = 2)
                if(newItem[0] != "*CMDR*"){
                    addCard(newItem[1], commanderIndex)

                }
            }
        }

    }


    private fun findCommander(deckListArray: List<String>?): String {
        if (deckListArray != null) {
            for (item in deckListArray){
                val newItem = item.split(" ", ignoreCase = true, limit = 2)
                if (newItem[0] == "*CMDR*"){
                    return newItem[1]
                }
            }
        }
        return "null"
    }

    private fun addCommander(commanderName: String) : Int {
        println(commanderName)
        val cmdrs = getCmdrHashMap()



        for(i in 0..16){
            println("loop# $i")
            if (cmdrs != null) {
                if (!cmdrs.containsKey(i)) {
                    cmdrs[i] = commanderName


                    val newOutputStream = ObjectOutputStream(FileOutputStream("${applicationContext.filesDir} cmdrHashMap"))
                    newOutputStream.writeObject(cmdrs)
                    newOutputStream.close()
                    return i
                }
            }
        }
        return -1
    }

    private fun addCard(cardName: String, commanderKey:Int) {
        //load the listof cards
        val cardsInDecks = getCardsInDecks()


        println(commanderKey)
        //look for cards that match the name of passed card
        if (cardsInDecks != null) {
            for (item in cardsInDecks ){

                println("${item.cardName}   ${item.listOfCommanders}")

                //if name matches only add commanderKey
                if (item.cardName == cardName){
                    println("commander key added")
                    item.addCommander(commanderKey)
                    println(item.listOfCommanders)
                    val newOutputStream = ObjectOutputStream(FileOutputStream("${applicationContext.filesDir} cardsInDecks"))
                    newOutputStream.writeObject(cardsInDecks)
                    newOutputStream.close()
                    return
                }
            }//else add new card
            cardsInDecks.add(Card(cardName, commanderKey))
        }

        //save the listof cards
        val newOutputStream = ObjectOutputStream(FileOutputStream("${applicationContext.filesDir} cardsInDecks"))
        newOutputStream.writeObject(cardsInDecks)
        newOutputStream.close()
    }

    private fun getLegends() : ArrayList<String>{
        val returnList = ArrayList<String>()
        val inputStream = resources.openRawResource(R.raw.legendary_creatures)

        inputStream.bufferedReader().useLines { lines -> lines.forEach { returnList.add(it)} }

        return returnList

    }

    private fun getCardsInDecks() : ArrayList<Card>?{
        val cardsInDecks : ArrayList<Card>
        val cardInputStream = ObjectInputStream(FileInputStream("${applicationContext.filesDir} cardsInDecks"))
        cardsInDecks = cardInputStream.readObject() as ArrayList<Card>
        cardInputStream.close()
        return cardsInDecks
    }

    private fun getCmdrHashMap() : HashMap<Int, String>?{
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
            //set up AlertDialog:dialogbuilder to be used later
            val cmdrNameAlertDialog = AlertDialog.Builder(this).create()
            //inflate the enter_cmdr_dialog View
            val inflater = layoutInflater
            val dialogView = inflater.inflate(R.layout.enter_cmdr_dialog, null)
            //get autocompleteView
            val enterCMDR: AutoCompleteTextView = dialogView.findViewById(R.id.cmdr_name)
            val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getLegends())
            enterCMDR.setAdapter(adapter)

            val buttonSubmit: Button = dialogView.findViewById(R.id.buttonSubmit)
            val buttonCancel: Button = dialogView.findViewById(R.id.buttonCancel)

            buttonCancel.setOnClickListener {
                cmdrNameAlertDialog.dismiss()
            }

            buttonSubmit.setOnClickListener {
                val goToAddDeckScreen = Intent(applicationContext, AddDeckScreen::class.java)
                goToAddDeckScreen.putExtra("CMDR Name", enterCMDR.text.toString())
                startActivityForResult(goToAddDeckScreen, 1)

                cmdrNameAlertDialog.dismiss()

            }
            cmdrNameAlertDialog.setView(dialogView)
            cmdrNameAlertDialog.show()
        }
        return true
    }

    private fun drawCmdrList(){
        //load cmdrhashmap
        val commanderHashMap = getCmdrHashMap()

        //make listof values from hashmap
        //var cmdrList : ArrayList<String> = ArrayList()
        //if (commanderHashMap != null) {
        //    for(item in commanderHashMap.values){
        //        cmdrList.add(item)
        //    }
        //}

        //get listview
        val cmdrRecyclerView = findViewById<View>(R.id.cmdr_name_list) as RecyclerView
        val adapter = commanderHashMap?.let { CommanderAdapter(it) }
        //apply adapter
        cmdrRecyclerView.adapter = adapter
        cmdrRecyclerView.layoutManager = LinearLayoutManager(this)
        if(commanderHashMap?.size?.rem(2) == 0){
            cmdrRecyclerView.setBackgroundColor(ContextCompat.getColor(cmdrRecyclerView.context, R.color.commander_background_1))
        }else {
            cmdrRecyclerView.setBackgroundColor(ContextCompat.getColor(cmdrRecyclerView.context, R.color.commander_background_2))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null && resultCode == -1){
            addDeckToLibrary(data.getStringExtra("DeckList String"))
        }

        drawCmdrList()

    }

}