package com.example.calculator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import net.objecthunter.exp4j.ExpressionBuilder
import com.example.calculator.databinding.ActivityCalculatorBinding

class CalculatorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCalculatorBinding
    private var expression = ""
    private var expressionText = ""

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
            button?.setOnClickListener { view ->
                if (view is Button) {
                    val buttonText = view.text?.toString() ?: return@setOnClickListener
                    appendToExpression(buttonText)
                }
            }
        }

        binding.back.setOnClickListener {
            removeLastCharacter()
        }
    }

    private fun appendToExpression(value: String) {
        val correctedValue = when (value) {
            "×" -> "*"
            "÷" -> "/"
            "," -> "."
            else -> value
        }
        expression += correctedValue
        expressionText += value
        updateExpression()
    }

    private fun removeLastCharacter() {
        if (expression.isNotEmpty()) {
            expression = expression.substring(0, expression.length - 1)
            expressionText = expressionText.substring(0, expressionText.length - 1)
        }
        updateExpression()
    }

    private fun updateExpression() {
        binding.expression.text = expressionText
        calculateResult()
    }

    private fun calculateResult() {
        try {
            val expr = ExpressionBuilder(expression).build()
            val result = expr.evaluate()

            binding.expression.setTextColor(getColor(R.color.black))
            binding.result.setTextColor(getColor(R.color.black))
            binding.result.text = result.toString()
        } catch (e: ArithmeticException) {
            binding.expression.setTextColor(getColor(R.color.red))
            binding.result.setTextColor(getColor(R.color.red))
            binding.result.text = getString(R.string.zeroDivisionError)
        } catch (e: Exception) {
            binding.expression.setTextColor(getColor(R.color.red))
            binding.result.setTextColor(getColor(R.color.red))
            binding.result.text = getString(R.string.error)
        }
    }
}