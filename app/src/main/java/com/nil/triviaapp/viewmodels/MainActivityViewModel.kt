package com.nil.triviaapp.viewmodels

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nil.triviaapp.R
import com.nil.triviaapp.fragments.MultipleSelectQuestionFragment
import com.nil.triviaapp.fragments.SingleSelectQuestionFragment
import com.nil.triviaapp.fragments.SummaryFragment
import com.nil.triviaapp.models.AnsweredQuestions
import com.nil.triviaapp.models.Questions
import com.nil.triviaapp.repositories.QuestionRepo
import es.dmoral.toasty.Toasty
import java.security.AccessControlContext

class MainActivityViewModel : ViewModel() {

    /**
     * Mutable live data variables
     * userName: Stores the the name provided by the user
     * questions: Stores the questions retrieved from the repository
     */
    private var userName : MutableLiveData<String> = MutableLiveData<String>()
    private var questions : MutableLiveData<ArrayList<AnsweredQuestions>> = MutableLiveData<ArrayList<AnsweredQuestions>>()

    /**
     * Saves the name provided by the user on the initial fragment
     * @param UserName: Name provided by the user
     */
    fun setUserName(UserName : String) {
        userName.value = UserName
    }

    /**
     * Retrieves the saves user name
     */
    fun getUserName(): LiveData<String> {
        return userName
    }

    /**
     * Store the questions and answers gives by the user
     */
    fun storeQuestions(questions: AnsweredQuestions) {
        //if there is already answered questions saved retrieve the instance of the arraylist
        //else create a new arraylist
        val currentQuestions : ArrayList<AnsweredQuestions> = if (this.questions.value != null) {
            this.questions.value!!
        } else {
            ArrayList()
        }
        //add new answered questions to the arraylist
        currentQuestions.add(questions)
        //save the arraylist as mutable live data
        this.questions.value = currentQuestions
    }

    /**
     * retrive the saves answered questions
     */
    fun storedQuestions() : LiveData<ArrayList<AnsweredQuestions>> {
        return questions
    }

    /**
     * get the questions from the repository
     */
    fun getQuestions(): LiveData<List<Questions>> {
        return QuestionRepo.getQuestions()
    }

    /**
     * based on the type of question display the appropriate fragment
     * if there are no more questions, display the summary fragment
     */
    fun nextQuestion(activity : FragmentActivity, context: Context, param : Int, questions : List<Questions>) {
        if (questions.count() <= param) {
            try {
                val ft: FragmentTransaction = activity.supportFragmentManager.beginTransaction()
                ft.replace(R.id.layout_inflate, SummaryFragment.newInstance(), "DisplaySummary")
                ft.commit()
            } catch (err: Exception) {
                Toasty.error(context, "Error: $err", Toast.LENGTH_SHORT, false).show()
            }
            return
        }

        val type = questions[param].type

        if (type.isNotBlank() && type == "Single") {
            try {
                val ft: FragmentTransaction = activity.supportFragmentManager.beginTransaction()
                ft.replace(
                    R.id.layout_inflate,
                    SingleSelectQuestionFragment.newInstance(param + 1), "DisplayQuestion")
                ft.commit()
            } catch (err: Exception) {
                Toasty.error(context, "Error: $err", Toast.LENGTH_SHORT, false).show()
            }
        }

        if (type.isNotBlank() && type == "Multiple") {
            try {
                val ft: FragmentTransaction = activity.supportFragmentManager.beginTransaction()
                ft.replace(R.id.layout_inflate, MultipleSelectQuestionFragment.newInstance(param + 1), "DisplayQuestion")
                ft.commit()
            } catch (err: Exception) {
                Toasty.error(context, "Error: $err", Toast.LENGTH_SHORT, false).show()
            }
        }
    }
}