package com.example.commanderorganizer

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.NetworkOnMainThreadException
import android.util.EventLog
import android.view.KeyEvent.ACTION_DOWN
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.MotionEvent.ACTION_DOWN
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.squareup.picasso.Picasso
import org.json.JSONObject
import java.io.*
import java.net.URL
import java.util.*
import kotlin.collections.HashMap

@Suppress("UNCHECKED_CAST")
class CardScreen : AppCompatActivity() {

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.card_screen_layout)

        val passedCard = intent.extras?.get("Card") as Card
        val commanderHashMap = getCmdrHashMap()
        val cardCmdrHashMap = HashMap<Int, String>()

        println(passedCard.cardName)
        //https://api.scryfall.com/cards/named?exact=${passedCard.cardName}

        val myQueue = Volley.newRequestQueue(this)
        val url = "https://api.scryfall.com/cards/named?fuzzy=${passedCard.cardName.replace(" ", "").replace("'", "")}"
        var cardJsonObject = JsonObject()


        val cardStringRequest = StringRequest(Request.Method.GET, url,
                Response.Listener { response ->
                    cardJsonObject = (JsonParser().parse(response).asJsonObject)
                    println("card url is $url")
                    val urlList = cardJsonObject.getAsJsonObject("image_uris")
                    if (urlList == null){
                        val arrayItem = cardJsonObject.getAsJsonArray("card_faces")

                        val frontFace = arrayItem.get(0).asJsonObject
                        val frontFaceUrlList = frontFace.getAsJsonObject("image_uris")
                        val frontFaceImageUrl = frontFaceUrlList.get("png").asString

                        val backFace = arrayItem.get(1).asJsonObject
                        val backFaceUrlList = backFace.getAsJsonObject("image_uris")
                        val backFaceImageUrl = backFaceUrlList.get("png").asString



                        val cardImage = findViewById<ImageView>(R.id.card_image)
                        Picasso.get()
                            .load(frontFaceImageUrl)
                            .into(cardImage)

                        cardImage.setOnTouchListener { v , event ->
                            when (event.action) {
                                MotionEvent.ACTION_DOWN -> {
                                    Picasso.get()
                                        .load(backFaceImageUrl)
                                        .into(findViewById<ImageView>(R.id.card_image))
                                    return@setOnTouchListener true
                                }
                                MotionEvent.ACTION_UP -> {
                                    Picasso.get()
                                        .load(frontFaceImageUrl)
                                        .into(findViewById<ImageView>(R.id.card_image))
                                    return@setOnTouchListener true

                                }
                            }
                            return@setOnTouchListener false

                        }

//                        cardImage.setOnTouchListener { v, event ->
//                            event.getAction() == MotionEvent.ACTION_DOWN
//                                Picasso.get()
//                                    .load(backFaceImageUrl)
//                                    .into(v)
//                        }

                    }else {
                        println("list of image urls $urlList")
                        val imageUrl = urlList.get("png").asString
                        val cardImage = findViewById<ImageView>(R.id.card_image)
                        Picasso.get()
                            .load(imageUrl)
                            .into(cardImage)
                    }
                },
            Response.ErrorListener {
                println("FAILED")
            })
        myQueue.add(cardStringRequest)

















        for (item in commanderHashMap) {
            if (item.key in passedCard.listOfCommanders) {
                cardCmdrHashMap.put(item.key, item.value)
            }
        }

        //get views
        val cardScreenCardName = findViewById<TextView>(R.id.card_screen_card_name)
        val commanderNameRecyclerView = findViewById<RecyclerView>(R.id.card_screen_commander_list)

        //set commander name gradient background
        cardScreenCardName.background = CreateGradient(applicationContext, passedCard.cardName).getBackgroundGradient()
        cardScreenCardName.text = passedCard.cardName

        //set up the adapter for the RecyclerView
        commanderNameRecyclerView.adapter = CardScreenCommanderAdapter(cardCmdrHashMap)
        commanderNameRecyclerView.layoutManager = LinearLayoutManager(this)

        if (cardCmdrHashMap.size % 2 == 0) {
            commanderNameRecyclerView.setBackgroundColor(ContextCompat.getColor(commanderNameRecyclerView.context, R.color.card_background_1))
        } else {
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