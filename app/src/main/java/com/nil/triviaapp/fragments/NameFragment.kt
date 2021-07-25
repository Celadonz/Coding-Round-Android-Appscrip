package com.nil.triviaapp.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.nil.triviaapp.R
import com.nil.triviaapp.databinding.FragmentNameBinding
import com.nil.triviaapp.viewmodels.MainActivityViewModel
import es.dmoral.toasty.Toasty


/**
 * Ask the user his/her name and store it in the associated view model
 */
class NameFragment : Fragment() {

    private lateinit var binding : FragmentNameBinding
    private lateinit var mainActivityViewModel: MainActivityViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //using view binding
        binding = FragmentNameBinding.inflate(inflater, container, false)

        //initiate view model
        mainActivityViewModel = ViewModelProvider(requireActivity()).get(MainActivityViewModel::class.java)

        //get the type of question
        val questions = mainActivityViewModel.getQuestions().value
        val type = questions?.get(0)?.type

        //next button click
        binding.buttonNext.setOnClickListener {

            //inputted name
            val nameValue = binding.name.text

            //if the user has not provided a name ask the user to do so
            //else display the appropriate fragment based on the type of question the first question in the repository is
            if (nameValue.isNullOrBlank()) {
                Toasty.warning(requireContext(), "Name Cannot be Empty", Toast.LENGTH_SHORT, false).show()
            } else {
                if (!type.isNullOrBlank() && type == "Single") {
                    try {
                        mainActivityViewModel.setUserName(nameValue.toString())

                        val ft: FragmentTransaction? = activity?.supportFragmentManager?.beginTransaction()
                        ft?.replace(R.id.layout_inflate, SingleSelectQuestionFragment.newInstance(1), "DisplayQuestion")
                        ft?.commit()
                    } catch (err: Exception) {
                        Toasty.error(requireContext(), "Error: $err", Toast.LENGTH_SHORT, false).show()
                    }
                }

                if (!type.isNullOrBlank() && type == "Multiple") {
                    try {
                        mainActivityViewModel.setUserName(nameValue.toString())

                        val ft: FragmentTransaction? = activity?.supportFragmentManager?.beginTransaction()
                        ft?.replace(R.id.layout_inflate, MultipleSelectQuestionFragment.newInstance(1), "DisplayQuestion")
                        ft?.commit()
                    } catch (err: Exception) {
                        Toasty.error(requireContext(), "Error: $err", Toast.LENGTH_SHORT, false).show()
                    }
                }
            }


        }

        return binding.root
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     */
    companion object {
        @JvmStatic
        fun newInstance() =
            NameFragment().apply {}
    }
}