package com.example.job1

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import kotlin.math.pow

class BmiCalFragment : Fragment() {
    private lateinit var seekBar: SeekBar
    private lateinit var height: TextView
    private lateinit var weight: TextView
    private lateinit var weightPlus: ImageView
    private lateinit var weightMinus: ImageView
    private lateinit var age: TextView
    private lateinit var agePlus: ImageView
    private lateinit var ageMinus: ImageView
    private lateinit var male: RelativeLayout
    private lateinit var female: RelativeLayout
    private lateinit var btn: AppCompatButton
    private var isBackground1: Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_bmi_cal, container, false)
        seekBar = view.findViewById(R.id.seekbar)
        height = view.findViewById(R.id.height)
        weight = view.findViewById(R.id.weight)
        weightPlus = view.findViewById(R.id.weightPlus)
        weightMinus = view.findViewById(R.id.weightMinus)
        age = view.findViewById(R.id.age)
        agePlus = view.findViewById(R.id.agePlus)
        ageMinus = view.findViewById(R.id.ageMinus)
        male = view.findViewById(R.id.male)
        female = view.findViewById(R.id.female)
        btn = view.findViewById(R.id.btn)

        male.setOnClickListener {
            toggleBackground()
            female.setBackgroundResource(R.drawable.background)
        }

        female.setOnClickListener {
            toggleBackground()
            male.setBackgroundResource(R.drawable.background)
        }

        weightPlus.setOnClickListener {
            increaseValue(weight)
        }

        weightMinus.setOnClickListener {
            decreaseValue(weight)
        }

        agePlus.setOnClickListener {
            increaseValue(age)
        }

        ageMinus.setOnClickListener {
            decreaseValue(age)
        }

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                height.text = progress.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        btn.setOnClickListener {
            calculateBMI()
        }

        return view
    }

    private fun calculateBMI() {
        val heightValue = seekBar.progress
        val weightValue = weight.text.toString().toInt()
        val ageValue = age.text.toString().toInt()

        val bmi = calculateBMIValue(heightValue, weightValue)

        val intent = Intent(requireActivity(), BmiResultActivity::class.java)
        intent.putExtra("BMI", bmi)
        startActivity(intent)
    }

    private fun calculateBMIValue(height: Int, weight: Int): Double {
        val heightInMeters: Double = height / 100.0
        return weight / heightInMeters.pow(2)
    }

    private fun toggleBackground() {
        if (isBackground1) {
            male.setBackgroundResource(R.drawable.background)
            female.setBackgroundResource(R.drawable.onclickbackground)
        } else {
            male.setBackgroundResource(R.drawable.onclickbackground)
            female.setBackgroundResource(R.drawable.background)
        }
        isBackground1 = !isBackground1
    }

    private fun increaseValue(textView: TextView) {
        val currentValue = textView.text.toString().toInt()
        textView.text = (currentValue + 1).toString()
    }

    private fun decreaseValue(textView: TextView) {
        val currentValue = textView.text.toString().toInt()
        if (currentValue > 0) {
            textView.text = (currentValue - 1).toString()
        }
    }
}
