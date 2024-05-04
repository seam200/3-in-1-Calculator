package com.example.job1

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class BmiResultActivity : AppCompatActivity() {
    lateinit var bmiType: TextView
    lateinit var bmiHolder: TextView
    lateinit var bmiImg: ImageView
    lateinit var main: ConstraintLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmi_result)

        bmiType=findViewById(R.id.textView2)
        bmiHolder=findViewById(R.id.bmiHolder)
        bmiImg=findViewById(R.id.imageView3)
        main=findViewById(R.id.main)

        val bmi = intent.getDoubleExtra("BMI", 0.0)

        Log.d("ResultActivity", "Received BMI: $bmi")
        if (bmi<18.5){
            bmiType.text="Under Weight"
            bmiHolder.text=String.format("%.2f", bmi)
            bmiImg.setBackgroundResource(R.drawable.underweight)
            main.setBackgroundColor(Color.parseColor("#FA6775"))
        }
        else if (18.5<bmi && bmi<24.9){
            bmiType.text="Healthy Weight"
            bmiHolder.text=String.format("%.2f", bmi)
            bmiImg.setBackgroundResource(R.drawable.healthy)
            main.setBackgroundColor(Color.parseColor("#7CFC00"))
        }
        else if (25.0<bmi && bmi<29.9){
            bmiType.text="Overweight"
            bmiHolder.text=String.format("%.2f", bmi)
            bmiImg.setBackgroundResource(R.drawable.overweight)
            main.setBackgroundColor(Color.parseColor("#FA6775"))
        }
        else{
            bmiType.text="Obesity"
            bmiHolder.text=String.format("%.2f", bmi)
            bmiImg.setBackgroundResource(R.drawable.obesity)
            main.setBackgroundColor(Color.parseColor("#FF0000"))
        }



    }
}