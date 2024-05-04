package com.example.job1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.job1.databinding.FragmentSimpleCalBinding
import kotlin.math.sqrt

class SimpleCalFragment : Fragment() {
    private var _binding: FragmentSimpleCalBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSimpleCalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set click listeners for number buttons
        binding.bzero.setOnClickListener { appendNumber("0") }
        binding.b1.setOnClickListener { appendNumber("1") }
        binding.b2.setOnClickListener { appendNumber("2") }
        binding.b3.setOnClickListener { appendNumber("3") }
        binding.b4.setOnClickListener { appendNumber("4") }
        binding.b5.setOnClickListener { appendNumber("5") }
        binding.b6.setOnClickListener { appendNumber("6") }
        binding.b7.setOnClickListener { appendNumber("7") }
        binding.b8.setOnClickListener { appendNumber("8") }
        binding.b9.setOnClickListener { appendNumber("9") }
        binding.bdzero.setOnClickListener { appendNumber("00") }
        binding.bdot.setOnClickListener { appendDot() }

        // Set click listeners for operator buttons
        binding.bAC.setOnClickListener { clear() }
        binding.bdlt.setOnClickListener { deleteLastCharacter() }
        binding.bdivd.setOnClickListener { selectOperator("/") }
        binding.bmulti.setOnClickListener { selectOperator("*") }
        binding.bminus.setOnClickListener { selectOperator("-") }
        binding.bplus.setOnClickListener { selectOperator("+") }
        binding.bEquel.setOnClickListener { calculateResult() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun appendNumber(number: String) {
        binding.textView5.append(number)
    }

    private fun appendDot() {
        if (!binding.textView5.text.contains(".")) {
            binding.textView5.append(".")
        }
    }

    private fun clear() {
        binding.textView5.text = ""
    }

    private fun deleteLastCharacter() {
        val currentText = binding.textView5.text.toString()
        if (currentText.isNotEmpty()) {
            binding.textView5.text = currentText.dropLast(1)
        }
    }

    private fun selectOperator(operator: String) {
        val currentText = binding.textView5.text.toString()
        if (currentText.isNotEmpty() && currentText.last() !in setOf('+', '-', '*', '/')) {
            binding.textView5.append(operator)
        }
    }

    private fun calculateResult() {
        val expression = binding.textView5.text.toString()
        val result = eval(expression)
        binding.textView5.text = result.toString()
    }

    private fun eval(expression: String): Double {
        val operators = listOf("+", "-", "*", "/")
        val tokens = expression.split(Regex("(?<=[-+*/])|(?=[-+*/])")).map { it.trim() }

        // Convert infix notation to postfix notation (Reverse Polish Notation)
        val postfix = mutableListOf<String>()
        val stack = mutableListOf<String>()

        for (token in tokens) {
            if (token.matches(Regex("-?\\d+(\\.\\d+)?"))) {
                postfix.add(token)
            } else if (token in operators) {
                while (stack.isNotEmpty() && precedence(stack.last()) >= precedence(token)) {
                    postfix.add(stack.removeAt(stack.lastIndex))
                }
                stack.add(token)
            } else if (token == "(") {
                stack.add(token)
            } else if (token == ")") {
                while (stack.isNotEmpty() && stack.last() != "(") {
                    postfix.add(stack.removeAt(stack.lastIndex))
                }
                stack.removeAt(stack.lastIndex)
            }
        }
        postfix.addAll(stack.reversed())

        // Evaluate postfix expression
        val evalStack = mutableListOf<Double>()
        for (token in postfix) {
            if (token.matches(Regex("-?\\d+(\\.\\d+)?"))) {
                evalStack.add(token.toDouble())
            } else if (token in operators) {
                val b = evalStack.removeAt(evalStack.lastIndex)
                val a = evalStack.removeAt(evalStack.lastIndex)
                val result = when (token) {
                    "+" -> a + b
                    "-" -> a - b
                    "*" -> a * b
                    "/" -> a / b
                    else -> throw IllegalArgumentException("Unknown operator: $token")
                }
                evalStack.add(result)
            }
        }
        return evalStack.first()
    }

    private fun precedence(operator: String): Int {
        return when (operator) {
            "+", "-" -> 1
            "*", "/" -> 2
            else -> 0
        }
    }
}
