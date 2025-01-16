package com.example.calculator

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import net.objecthunter.exp4j.ExpressionBuilder
import com.example.calculator.databinding.ActivityCalculatorBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class CalculatorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCalculatorBinding
    private val viewModel by lazy {
        ViewModelProvider(this)[CalculatorViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalculatorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val buttons = listOf(
            binding.zero, binding.one, binding.two, binding.three, binding.four,
            binding.five, binding.six, binding.seven, binding.eight, binding.nine,
            binding.plus, binding.minus, binding.multiply, binding.divide,
            binding.comma
        )

        buttons.forEach { button ->
            button?.setOnClickListener { handle(Event.EnterNumber(button.text.toString())) }
        }

        lifecycleScope.launch {
            viewModel.state.collect { state -> render(state) }
        }
    }

    private fun render(state: State) {
        binding.expression.text = state.expression
        binding.result.text = state.expressionText
        binding.result.setTextColor(if (state.expressionText.startsWith("Ошибка")) Color.RED else Color.BLACK)
    }

    private fun handle(event: Event) {
        viewModel.handle(event)
    }
}