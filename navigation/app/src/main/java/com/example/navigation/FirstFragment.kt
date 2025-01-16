package com.example.navigation

import android.os.Bundle
import android.text.Editable
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.navigation.databinding.FragmentFirstBinding

class FirstFragment : Fragment(R.layout.fragment_first)
{
        private object Constants {
            const val FIRST_NAME = "FIRST_NAME"
            const val LAST_NAME = "LAST_NAME"
        }

    private lateinit var binding: FragmentFirstBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFirstBinding.bind(view)

        val firstNameArg = arguments?.getString(Constants.FIRST_NAME)
        val lastNameArg = arguments?.getString(Constants.LAST_NAME)

        if (firstNameArg != null && lastNameArg != null)
        {
            binding.firstName.text = Editable.Factory.getInstance().newEditable(firstNameArg)
            binding.lastName.text = Editable.Factory.getInstance().newEditable(lastNameArg)
        }

        binding.secondFragmentButton.setOnClickListener {
            val firstName:String = binding.firstName.text.toString()
            val lastName:String = binding.lastName.text.toString()

            val arguments = Bundle().apply {
                putString(Constants.FIRST_NAME, firstName)
                putString(Constants.LAST_NAME, lastName)
            }

            findNavController()
                .navigate(R.id.action_firstFragment_to_secondFragment, arguments)
        }

        binding.forthFragmentButton.setOnClickListener {
            findNavController().navigate(R.id.action_firstFragment_to_forthFragment)
        }
    }
}