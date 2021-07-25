package com.nil.triviaapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.nil.triviaapp.R
import com.nil.triviaapp.databinding.FragmentSingleSelectQuestionBinding
import com.nil.triviaapp.models.AnsweredQuestions
import com.nil.triviaapp.viewmodels.MainActivityViewModel
import es.dmoral.toasty.Toasty

/**
 * Display a question with multiple chooses
 * The user can select a single option
 */

//parameter
private const val ARG_PARAM = "param"

class SingleSelectQuestionFragment : Fragment() {
    //date send to the fragment
    private var param: Int? = null

    //view binding variable
    private lateinit var binding : FragmentSingleSelectQuestionBinding

    //view model variable
    private lateinit var mainActivityViewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param = it.getInt(ARG_PARAM)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //initiate view binding
        binding = FragmentSingleSelectQuestionBinding.inflate(inflater, container, false)

        //initiate view model
        mainActivityViewModel = ViewModelProvider(requireActivity()).get(MainActivityViewModel::class.java)

        //get question from repository based on the current position
        val questions = mainActivityViewModel.getQuestions().value
        val question = questions?.get(param!! - 1)?.question
        val optionOne = questions?.get(param!! - 1)?.optionOne
        val optionTwo = questions?.get(param!! - 1)?.optionTwo
        val optionThree = questions?.get(param!! - 1)?.optionThree
        val optionFour = questions?.get(param!! - 1)?.optionFour

        //display question
        binding.text.text = question
        binding.radiaId1.text = optionOne
        binding.radiaId2.text = optionTwo
        binding.radiaId3.text = optionThree
        binding.radiaId4.text = optionFour

        //next button click
        binding.buttonNext.setOnClickListener {
            // Getting the checked radio button id
            // from the radio group
            val selectedOption: Int = binding.groupradio.checkedRadioButtonId

            //if the user has not selected any option
            //ask the user to do so
            if (selectedOption != -1) {
                // Assigning id of the checked radio button
                val radioButton : RadioButton = binding.root.findViewById(selectedOption)

                //get text of selected button
                val text = radioButton.text

                //store answered question
                mainActivityViewModel.storeQuestions(AnsweredQuestions(question!!, text.toString()))

                //display next question
                mainActivityViewModel.nextQuestion(requireActivity(), requireContext(), param!!, questions)
            } else {
                Toasty.warning(requireContext(), "Please select an option", Toast.LENGTH_SHORT, false).show()
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
        fun newInstance(param: Int) =
            SingleSelectQuestionFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM, param)
                }
            }
    }
}