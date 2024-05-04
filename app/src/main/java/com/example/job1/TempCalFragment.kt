package com.example.job1
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment

class TempCalFragment : Fragment() {
    private lateinit var spinnerFrom: Spinner
    private lateinit var spinnerTo: Spinner
    private lateinit var editTextValue: EditText
    private lateinit var buttonConvert: Button
    private lateinit var textViewResult: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_temp_cal, container, false)

        // Initialize views
        spinnerFrom = view.findViewById(R.id.spinnerFrom)
        spinnerTo = view.findViewById(R.id.spinnerTo)
        editTextValue = view.findViewById(R.id.editTextValue)
        buttonConvert = view.findViewById(R.id.buttonConvert)
        textViewResult = view.findViewById(R.id.textViewResult)

        // Set up spinners
        val temperatureUnits = arrayOf("Celsius", "Kelvin", "Fahrenheit")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, temperatureUnits)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerFrom.adapter = adapter
        spinnerTo.adapter = adapter

        // Set up button click listener
        buttonConvert.setOnClickListener {
            convertTemperature()
        }

        return view
    }

    private fun convertTemperature() {
        val inputValue = editTextValue.text.toString().toDoubleOrNull()

        if (inputValue != null) {
            val fromUnit = spinnerFrom.selectedItem.toString()
            val toUnit = spinnerTo.selectedItem.toString()

            val result = when (fromUnit) {
                "Celsius" -> {
                    when (toUnit) {
                        "Kelvin" -> inputValue + 273.15
                        "Fahrenheit" -> (inputValue * 9 / 5) + 32
                        else -> inputValue
                    }
                }
                "Kelvin" -> {
                    when (toUnit) {
                        "Celsius" -> inputValue - 273.15
                        "Fahrenheit" -> (inputValue - 273.15) * 9 / 5 + 32
                        else -> inputValue
                    }
                }
                "Fahrenheit" -> {
                    when (toUnit) {
                        "Celsius" -> (inputValue - 32) * 5 / 9
                        "Kelvin" -> (inputValue - 32) * 5 / 9 + 273.15
                        else -> inputValue
                    }
                }
                else -> inputValue
            }

            textViewResult.text = "%.2f".format(result)
        } else {
            textViewResult.text = "Invalid input"
        }
    }
}
