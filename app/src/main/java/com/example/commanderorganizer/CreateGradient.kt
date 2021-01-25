package com.example.commanderorganizer

import android.content.Context
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.drawable.GradientDrawable
import androidx.core.content.ContextCompat

class CreateGradient (val context: Context, private val cardName : String?){

    fun getBackgroundGradient() : GradientDrawable{
        var cardColors = listOf<String>()
        val inputStream = context.resources.openRawResource(R.raw.allcards_coloridentity_manacost)
        val commanders = inputStream.bufferedReader().readLines()
        for (line in commanders){
            if(line.split("|")[0] == cardName)
                cardColors = line.split("|")[1].split(",")
        }

        inputStream.close()
        val gradientColorArray = mutableListOf<Int>()

        if ("W" in cardColors) gradientColorArray.add(ContextCompat.getColor(context, R.color.gradient_white))

        if ("U" in cardColors) gradientColorArray.add(ContextCompat.getColor(context, R.color.gradient_blue))

        if ("B" in cardColors) gradientColorArray.add(ContextCompat.getColor(context, R.color.gradient_black))

        if ("R" in cardColors) gradientColorArray.add(ContextCompat.getColor(context, R.color.gradient_red))

        if ("G" in cardColors) gradientColorArray.add(ContextCompat.getColor(context, R.color.gradient_green))




        //if only 1 color returns array of WW, BB etc
        if(gradientColorArray.size == 0){
            return GradientDrawable(
                    GradientDrawable.Orientation.LEFT_RIGHT,
                    intArrayOf(Color.parseColor("#ADADAD"), Color.parseColor("#ADADAD"))
            )

        }
        if (gradientColorArray.size == 1){
            return GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, intArrayOf(gradientColorArray[0], gradientColorArray[0]))
        }
        //else
        return GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, gradientColorArray.toIntArray())

    }


    //fun getTextGradient(): LinearGradient {


    //}



}