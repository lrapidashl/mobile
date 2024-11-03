package com.example.navigation

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.navigation.databinding.FragmentThirdBinding

class ThirdFragment : Fragment(R.layout.fragment_third)
{
    private object Constants {
        const val FIRST_NAME = "FIRST_NAME"
        const val LAST_NAME = "LAST_NAME"
        const val YEAR = "YEAR"
        const val MONTH = "MONTH"
        const val DAY = "DAY"
    }

    private lateinit var binding: FragmentThirdBinding

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentThirdBinding.bind(view)

        val year = arguments?.getInt(Constants.YEAR)
        val month = arguments?.getInt(Constants.MONTH)
        val day = arguments?.getInt(Constants.DAY)
        val firstName = arguments?.getString(Constants.FIRST_NAME)
        val lastName = arguments?.getString(Constants.LAST_NAME)

        if (year != null && month != null && day != null) {
            val dateText = "Selected date: $day/${month + 1}/$year\n$firstName $lastName"
            binding.thirdText.text = dateText
        } else {
            binding.thirdText.text = "No date selected"
        }

        binding.firstFragmentButton.setOnClickListener {
            val arguments = Bundle().apply {
                putString("FIRST_NAME", firstName)
                putString("LAST_NAME", lastName)
            }
            findNavController().navigate(R.id.action_thirdFragment_to_firstFragment, arguments)
        }
    }
}