package com.example.commanderorganizer

import java.io.Serializable

class Card(val cardName: String, var commanderValue: Int) : Serializable {
    var listOfCommanders = ArrayList<Int>()

    init{
        listOfCommanders.add(commanderValue)
    }


    fun addCommander(commanderValue: Int) {
        listOfCommanders.add(commanderValue)
    }

    fun removeCommander(commanderValue: Int) {
        listOfCommanders.remove(commanderValue)
    }

}