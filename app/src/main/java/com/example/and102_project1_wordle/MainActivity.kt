package com.example.and102_project1_wordle

import FourLetterWordList
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    var wordToGuess:String = FourLetterWordList.getRandomFourLetterWord()
    var guessNumber = 1;
    lateinit var arrayAdapter: CustomAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        var results: ArrayList<Triple<String, String,String>> = ArrayList()
        var submitButton: Button = findViewById(R.id.SubmitButton)
        var guessTextView: EditText = findViewById(R.id.guessTextView)
        var answerTextView: TextView = findViewById(R.id.answerTextView)
        answerTextView.text = wordToGuess


        var guestListView: ListView = findViewById(R.id.guessListView)
        arrayAdapter = CustomAdapter(this, results)
        guestListView.adapter = arrayAdapter



        submitButton.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {

                if(submitButton.text == "Check?") {
                    var guess: String = guessTextView.text.toString()
                    guess = guess.trim()
                    if (guess != "" && guess.length == 4 && guessNumber < 4) {

                        var currentResult = checkGuess(guess.uppercase())
                        results.add(Triple(guessNumber.toString(), guess, currentResult))
                        arrayAdapter.notifyDataSetChanged()
                        if(currentResult == "OOOO"){
                            answerTextView.text = "Congratulation !!!!"
                            answerTextView.visibility = View.VISIBLE
                            submitButton.text = "Reset"
                        }
                        guessNumber++
                        Log.i("Application Status", "Corrcet answer ${wordToGuess}")
                        if(guessNumber == 4 && currentResult != "OOOO"){
                            submitButton.text = "Reset"
                            answerTextView.visibility = View.VISIBLE
                            Toast.makeText(this@MainActivity, "Ohno!! you have used all chnaces", Toast.LENGTH_LONG).show()
                        }

                    } else {
                        Toast.makeText(this@MainActivity, "Enter 4 letter word", Toast.LENGTH_LONG)
                            .show()
                    }
                    guessTextView.text.clear()


                }else{
                    results.clear()
                    guessNumber = 1;
                    arrayAdapter.notifyDataSetChanged()
                    submitButton.text = "Check?"
                    wordToGuess = FourLetterWordList.getRandomFourLetterWord()
                    answerTextView.visibility = View.INVISIBLE
                    answerTextView.text = wordToGuess
                }

            }
        })




    }


    private fun checkGuess(guess: String) : String {
        var result = ""
        for (i in 0..3) {
            if (guess[i] == wordToGuess[i]) {
                result += "O"
            }
            else if (guess[i] in wordToGuess) {
                result += "+"
            }
            else {
                result += "X"
            }
        }
        return result
    }
}