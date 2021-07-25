package com.nil.triviaapp.repositories

import androidx.lifecycle.MutableLiveData
import com.nil.triviaapp.models.Questions


object QuestionRepo {

    /**
        The class is a singleton, meaning only one instance of the repository will be in existence at a time
     */

    //variables for repository of questions
    private val dataSet: ArrayList<Questions> = ArrayList()

    //initialize repository with default data
     init {
         dataSet.add(Questions("Who is the best cricketer in the world?", "Sachin Tendulkar", "Virat Kolli", "Adam Gilchirst", "Jacques Kallis", "Single"))
         dataSet.add(Questions("What are the colors in the Indian national flag? (Select all)", "White", "Yellow", "Orange", "Green", "Multiple"))
     }

    //getter for repository
    fun getQuestions(): MutableLiveData<List<Questions>> {
        val data: MutableLiveData<List<Questions>> = MutableLiveData<List<Questions>>()
        data.value = dataSet
        return data
    }
}