package com.bernarddiamante.TipCalcy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bernarddiamante.TipCalcy.databinding.ActivityMainBinding
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btCalculate.setOnClickListener { calculateTip() }
    }

    private fun calculateTip() {
        val stringInTextField = binding.etCostOfService.text.toString()
        val cost = stringInTextField.toDoubleOrNull()
        if (cost == null) {
            binding.tvTipResult.text = ""
            return
        }
        val tipPercentage = when (binding.rgTipOptions.checkedRadioButtonId) {
            R.id.rbOptionTwentyPercent -> 0.20
            R.id.rbOptionEighteenPercent -> 0.18
            else -> 0.15
        }
        var tip = tipPercentage * cost
        if (binding.swRoundUp.isChecked) {
            tip = kotlin.math.ceil(tip)
        }
        displayTip(tip)
    }

    private fun displayTip(tip: Double) {
        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
        binding.tvTipResult.text = getString(R.string.tip_amount, formattedTip)
    }
}