package com.nil.triviaapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.nil.triviaapp.R
import com.nil.triviaapp.databinding.FragmentMultipleSelectQuestionBinding
import com.nil.triviaapp.databinding.FragmentSingleSelectQuestionBinding
import com.nil.triviaapp.models.AnsweredQuestions
import com.nil.triviaapp.viewmodels.MainActivityViewModel
import es.dmoral.toasty.Toasty

/**
 * Display a question with multiple chooses
 * The user can select multiple options
 */

//parameter
private const val ARG_PARAM = "param"

class MultipleSelectQuestionFragment : Fragment() {
    //date send to the fragment
    private var param: Int? = null

    //variables
    //stores all ckecked values
    private lateinit var checked : ArrayList<String>

    //view binding variable
    private lateinit var binding: FragmentMultipleSelectQuestionBinding

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
        binding = FragmentMultipleSelectQuestionBinding.inflate(inflater, container, false)

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

        //the layout containing all the checkboxes
        //used to loop through the checkboxes later
        val linearLayout : LinearLayout = binding.groupcheck

        //next button click
        binding.buttonNext.setOnClickListener {
            checked = ArrayList()

            //loop through all the checkboxes
            for (i in 0 until linearLayout.childCount) {
                val v: View = linearLayout.getChildAt(i)
                if (v is CheckBox) {
                    //if any of the checkboxes are checked
                    if (v.isChecked) {
                        //get text of the selected checkbox
                        val text = v.text
                        if (!text.isNullOrBlank()) {
                            //store the text to an arraylist
                            checked.add(text.toString())
                        }
                    }
                }
            }

            //if the size of the arraylist is 0, meaning no checkboxes are selected
            //ask the use to select at least one
            if (checked.count() == 0) {
                Toasty.warning(requireContext(), "Please select an option", Toast.LENGTH_SHORT, false).show()
            } else {
                //convert the array list to a comma separate string
                val text = checked.joinToString(separator = ",")

                //store answered question in the associated vide model
                mainActivityViewModel.storeQuestions(AnsweredQuestions(question!!, text))

                //display next question
                mainActivityViewModel.nextQuestion(requireActivity(), requireContext(), param!!, questions)
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
            MultipleSelectQuestionFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM, param)
                }
            }
    }
}