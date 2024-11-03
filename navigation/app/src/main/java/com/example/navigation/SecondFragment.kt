package com.example.navigation

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.navigation.databinding.FragmentSecondBinding

class SecondFragment : Fragment(R.layout.fragment_second)
{
    private object Constants {
        const val FIRST_NAME = "FIRST_NAME"
        const val LAST_NAME = "LAST_NAME"
        const val YEAR = "YEAR"
        const val MONTH = "MONTH"
        const val DAY = "DAY"
    }

    private lateinit var binding: FragmentSecondBinding

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSecondBinding.bind(view)

        val firstName = arguments?.getString(Constants.FIRST_NAME)
        val lastName = arguments?.getString(Constants.LAST_NAME)

        binding.name.text = "$firstName $lastName"

        binding.thirdFragmentButton.setOnClickListener {
            val year = binding.datePicker.year
            val month = binding.datePicker.month
            val day = binding.datePicker.dayOfMonth

            val arguments = Bundle().apply {
                putInt(Constants.YEAR, year)
                putInt(Constants.MONTH, month)
                putInt(Constants.DAY, day)
                putString(Constants.FIRST_NAME, firstName)
                putString(Constants.LAST_NAME, lastName)
            }

            findNavController().navigate(R.id.action_secondFragment_to_thirdFragment, arguments)
        }
    }
}