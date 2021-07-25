package com.nil.triviaapp.fragments

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.nil.triviaapp.R
import com.nil.triviaapp.activities.MainActivity
import com.nil.triviaapp.databinding.FragmentSummaryBinding
import com.nil.triviaapp.helpers.DatabaseHelper
import com.nil.triviaapp.models.Data
import com.nil.triviaapp.viewmodels.MainActivityViewModel
import es.dmoral.toasty.Toasty
import java.text.SimpleDateFormat
import java.util.*

/**
 * Display a summary of the current game
 */
class SummaryFragment : Fragment() {

    //view binding variable
    private lateinit var binding: FragmentSummaryBinding

    //view model variable
    private lateinit var mainActivityViewModel: MainActivityViewModel

    //database helper variables
    lateinit var databaseHelper: DatabaseHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSummaryBinding.inflate(inflater, container, false)

        //instantiate view model
        mainActivityViewModel = ViewModelProvider(requireActivity()).get(MainActivityViewModel::class.java)

        //get stored data for summary
        val userName = mainActivityViewModel.getUserName().value
        val storedQuestions = mainActivityViewModel.storedQuestions().value

        //get instance of database helper
        databaseHelper = DatabaseHelper(context)

        //get current date
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss aa", Locale.US)
        val currentDate = sdf.format(Date())

        //generate uuid
        val uuid: UUID = UUID.randomUUID()
        val str: String = uuid.toString()

        //format text for summary
        var summaryText = "Hello $userName" + System.lineSeparator() + System.lineSeparator() + "Here are the answers selected:" + System.lineSeparator() + System.lineSeparator()
        storedQuestions?.forEach {
            databaseHelper.addData(Data(str, userName!!, it.question, it.answer, currentDate))
            summaryText += "${it.question} ${System.lineSeparator()} Answer: ${it.answer}" + System.lineSeparator() + System.lineSeparator()
        }

        //set summary text to appropriate text view
        binding.summary.text = summaryText.trim()

        //finish button click
        //restart the activity
        binding.finish.setOnClickListener {
            //activity?.recreate()

            //use for better animation
            activity?.finish()
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
        }

        //history button click
        //start the history fragment
        binding.history.setOnClickListener {
            try {
                val ft: FragmentTransaction? = activity?.supportFragmentManager?.beginTransaction()
                ft?.replace(R.id.layout_inflate, HistoryFragment.newInstance(), "DisplayHistory")
                ft?.addToBackStack(null)
                ft?.commit()
            } catch (err: Exception) {
                Toasty.error(requireContext(), "Error: $err", Toast.LENGTH_SHORT, false).show()
            }
        }

        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         */
        @JvmStatic
        fun newInstance() =
            SummaryFragment().apply {
                arguments = Bundle().apply {}
            }
    }
}