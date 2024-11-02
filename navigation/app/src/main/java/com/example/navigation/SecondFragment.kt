package com.example.navigation

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.navigation.databinding.FragmentSecondBinding

class SecondFragment : Fragment(R.layout.fragment_second)
{
    private lateinit var binding: FragmentSecondBinding

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSecondBinding.bind(view)

        val firstName = arguments?.getString("FIRST_NAME")
        val lastName = arguments?.getString("LAST_NAME")

        binding.name.text = "$firstName $lastName"

        binding.thirdFragmentButton.setOnClickListener {
            val year = binding.datePicker.year
            val month = binding.datePicker.month
            val day = binding.datePicker.dayOfMonth

            val arguments = Bundle().apply {
                putInt("YEAR", year)
                putInt("MONTH", month)
                putInt("DAY", day)
                putString("FIRST_NAME", firstName)
                putString("LAST_NAME", lastName)
            }

            findNavController().navigate(R.id.action_secondFragment_to_thirdFragment, arguments)
        }
    }
}