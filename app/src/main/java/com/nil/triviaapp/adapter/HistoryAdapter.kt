package com.nil.triviaapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nil.triviaapp.R
import com.nil.triviaapp.models.Data


class HistoryAdapter(private val dataSet: Map<String, List<Data>>):
    RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        //bind views
        val gameDate: TextView = view.findViewById(R.id.game_date)
        val playerNAme: TextView = view.findViewById(R.id.player_name)
        val questionAnswers: TextView = view.findViewById(R.id.question_answer)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.history, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        //get key at the current position
        val key = dataSet.keys.elementAt(position)
        val data = dataSet[key]

        //get the date and time for the current game
        val date = data?.get(0)?.time

        //format the string
        val header = "Game ${position + 1} : $date"

        //set appropriate text
        holder.gameDate.text = header

        //get the name of the player for the current game
        val name = data?.get(0)?.playerName

        //format string
        val nameText = "Name : $name"

        //set appropriate text
        holder.playerNAme.text = nameText

        //for the arraylist containing the answered questions for the current game
        var questionsStr = ""
        data?.forEach {
            //format string
            questionsStr += "${it.question} ${System.lineSeparator()} Answer: ${it.answer}" + System.lineSeparator() + System.lineSeparator()
        }
        //set appropriate text
        holder.questionAnswers.text = questionsStr.trim()
    }

    override fun getItemCount() = dataSet.size
}