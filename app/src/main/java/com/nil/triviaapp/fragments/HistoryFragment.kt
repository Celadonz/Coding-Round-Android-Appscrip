package com.nil.triviaapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.nil.triviaapp.R
import com.nil.triviaapp.adapter.HistoryAdapter
import com.nil.triviaapp.databinding.FragmentHistoryBinding
import com.nil.triviaapp.databinding.FragmentSummaryBinding
import com.nil.triviaapp.helpers.DatabaseHelper
import com.nil.triviaapp.viewmodels.MainActivityViewModel

/**
 * Display the history stored in a local database in the form of a list using recycler view
 */
class HistoryFragment : Fragment() {

    //view binding variable
    private lateinit var binding: FragmentHistoryBinding

    //view model variable
    private lateinit var mainActivityViewModel: MainActivityViewModel

    //database helper variables
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHistoryBinding.inflate(inflater, container, false)

        //get instance of view model
        mainActivityViewModel = ViewModelProvider(requireActivity()).get(MainActivityViewModel::class.java)

        //get instance of database helper
        databaseHelper = DatabaseHelper(context)

        //get data from database
        val data = databaseHelper.getData()

        //organize the data
        val organizedData = data.groupBy { it.gameID }

        //set up recycler view
        val adapter = HistoryAdapter(organizedData)
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter

        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         */
        @JvmStatic
        fun newInstance() =
            HistoryFragment().apply {
                arguments = Bundle().apply {}
            }
    }
}