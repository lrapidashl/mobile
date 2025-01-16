package com.example.clicker.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.clicker.BuildingsAdapter
import com.example.clicker.ClickerViewModel
import com.example.clicker.R
import com.example.clicker.databinding.FragmentShopBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ShopFragment : Fragment(R.layout.fragment_shop) {
    private var _binding: FragmentShopBinding? = null
    private val binding get() = _binding!!
    private val clickerGameView: ClickerViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShopBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = BuildingsAdapter(
        { building ->
            clickerGameView.buyBuilding(building)
        },
        { building ->
            clickerGameView.refundBuilding(building)
        })

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        clickerGameView.clickerUiState
            .onEach {
                clickerGameView.clickerUiState.collect {
                    adapter.submitList(clickerGameView.clickerUiState.value.buildings)
                    clickerGameView.updateBuildingsAvailability()
                }
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        clickerGameView.toastMessage
            .onEach {
                clickerGameView.toastMessage.collect { message ->
                    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                }
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}