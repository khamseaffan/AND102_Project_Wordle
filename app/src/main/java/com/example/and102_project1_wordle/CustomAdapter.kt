package com.example.and102_project1_wordle

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class CustomAdapter(private val context: Context, private val dataList: List<Triple<String, String, String>>) :
    ArrayAdapter<Triple<String, String, String>>(context, R.layout.list_item_layout_guess, dataList) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.list_item_layout_guess, parent, false)

        val guessNumberTextView = view.findViewById<TextView>(R.id.guessNumber)
        val guessTextTextView = view.findViewById<TextView>(R.id.guessText)
        val resultTextView = view.findViewById<TextView>(R.id.result)

        val dataItem = dataList[position]

        guessNumberTextView.text = "Guess # ${dataItem.first}"
        guessTextTextView.text = dataItem.second
        resultTextView.text = dataItem.third

        return view
    }
}