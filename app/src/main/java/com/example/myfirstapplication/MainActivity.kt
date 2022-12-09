package com.example.myfirstapplication

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.myfirstapplication.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity: AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var serviceType: String = ""

        fun getRandomPercent(): Float {
            val PercentageRange: HashMap<String, Array<Int>> = HashMap()
            PercentageRange.put("Terrible", arrayOf(0, 9))
            PercentageRange.put("Normal", arrayOf(10, 12))
            PercentageRange.put("Amazing", arrayOf(13, 20))

            if (PercentageRange.keys.contains(serviceType)) {
                return Random.nextInt(PercentageRange[serviceType]!!.first() * 100, PercentageRange[serviceType]!!.last() * 100) / 100.0F
            }
            return Random.nextInt(0, PercentageRange["Amazing"]!!.last() * 100) / 100.0F
        }
        fun setFinalPrice() {
            var multiplificationFactor = (1 + getRandomPercent()/100.0F)
            binding.numberView.setText("Final price is ${String.format("%.2f", binding.numberInput.text.toString().toFloat() * multiplificationFactor)}")
        }

        binding.numberInput.setOnFocusChangeListener {view, hasFocus ->
            if (!hasFocus) {
                binding.numberInput.setText(String.format("%.2f", binding.numberInput.text.toString().toFloat()))
            }
        }
        binding.numberInput.addTextChangedListener {
            if (!binding.numberInput.text.isEmpty()) {
                try {
                    binding.numberInput.text.toString().toFloat()

                    if (binding.numberInput.text.contains('.')) {
                        if (binding.numberInput.text.toString().indexOf('.') < binding.numberInput.text.length-3) {
                            binding.numberInput.clearFocus()
                        }
                    }
                    binding.DirectionalText.setText("Now describe how you were serviced!")
                    binding.buttonTerrible.isEnabled = true
                    binding.buttonAcceptable.isEnabled = true
                    binding.buttonAmazing.isEnabled = true
                } catch(e: Exception) {
                    binding.numberInput.setText(binding.numberInput.text.toString().substring(0, binding.numberInput.text.length-1))
                }
            } else {
                binding.DirectionalText.setText("Input the price:")
                binding.buttonTerrible.isEnabled = false
                binding.buttonAcceptable.isEnabled = false
                binding.buttonAmazing.isEnabled = false
            }
        }

        binding.buttonTerrible.setOnClickListener {
            serviceType = "Terrible"
            setFinalPrice()
        }
        binding.buttonAcceptable.setOnClickListener {
            serviceType = "Normal"
            setFinalPrice()
        }
        binding.buttonAmazing.setOnClickListener {
            serviceType = "Amazing"
            setFinalPrice()
        }
    }
}