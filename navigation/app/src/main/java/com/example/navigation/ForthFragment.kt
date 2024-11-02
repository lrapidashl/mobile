package com.example.navigation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.navigation.databinding.FragmentForthBinding

class ForthFragment : Fragment(R.layout.fragment_forth)
{
    private lateinit var binding: FragmentForthBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentForthBinding.bind(view)

        binding.firstFragmentButton.setOnClickListener {
            findNavController()
                .popBackStack()
        }

        binding.fifthFragmentButton.setOnClickListener {
            findNavController()
                .navigate(R.id.action_forthFragment_to_fifthFragment)
        }
    }
}