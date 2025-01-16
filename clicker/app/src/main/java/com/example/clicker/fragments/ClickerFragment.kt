package com.example.clicker.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.clicker.ClickerViewModel
import com.example.clicker.R
import com.example.clicker.databinding.FragmentClickerBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ClickerFragment : Fragment(R.layout.fragment_clicker) {
    private val clickerGameView: ClickerViewModel by activityViewModels()
    private lateinit var binding: FragmentClickerBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        binding = FragmentClickerBinding.bind(view)

        binding.cookieButton.setOnClickListener {
            clickerGameView.onCookieClicked()
            animateCookieClick()
        }

        clickerGameView.clickerUiState
            .onEach {
                clickerGameView.clickerUiState.collect { state ->
                    binding.cookieCount.text = getString(R.string.cookie_count, state.cookieCount)
                    binding.perSecond.text = getString(R.string.per_second, state.cookiesPerSecond)
                    binding.time.text = getString(R.string.time, state.elapsedTime)
                    binding.averageSpeed.text =
                        getString(R.string.average_speed, state.cookiesPerSecond * 60.0)
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

    private fun animateCookieClick() {
        binding.cookieButton.animate()
            .scaleX(0.9f)
            .scaleY(0.9f)
            .setDuration(100)
            .withEndAction {
                binding.cookieButton.animate().scaleX(1f).scaleY(1f).duration = 100
            }
    }
}