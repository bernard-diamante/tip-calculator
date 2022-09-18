package com.bernarddiamante.tipcalc

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.bernarddiamante.tipcalc.databinding.ActivityMainBinding
import java.text.NumberFormat
import kotlin.math.ceil

/*
* Activity that displays a tip calculator
*/
class MainActivity : AppCompatActivity() {

    // Binding object instance: has access to the views in activity_main.xml layout
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate the layout XML file and return a binding object instance
        binding = ActivityMainBinding.inflate(layoutInflater)

        // Set the content view of the Activity to be the root view of the layout
        setContentView(binding.root)

        // Set up a click listener on the calculate button to calculate the tip
        binding.btCalculate.setOnClickListener { calculateTip() }

        // Set up a key listener on the EditText field to listen for "enter" button presses
        binding.etCostOfService.setOnKeyListener { view, keyCode, _ ->
            handleKeyEvent(view, keyCode)
        }
    }

    /*
    * Calculates the tip based on user input
    */
    private fun calculateTip() {
        // Get the decimal value from the cost of service EditText field
        val stringInTextField = binding.etCostOfService.text.toString()
        val cost = stringInTextField.toDoubleOrNull()

        // If the cost is null or 0, then display 0 tip and exit this function early
        if (cost == null) {
            binding.tvTipResult.text = ""
            return
        }

        // Get the tip percentage based on the selected radio button
        val tipPercentage = when (binding.rgTipOptions.checkedRadioButtonId) {
            R.id.rbOptionTwentyPercent -> 0.20
            R.id.rbOptionEighteenPercent -> 0.18
            else -> 0.15
        }

        // Calculate the tip
        var tip = tipPercentage * cost

        // If the switch for rounding up is toggled on (isChecked in true), then round up the tip.
        // Otherwise, do not change the tip value.
        if (binding.swRoundUp.isChecked) {
            tip = ceil(tip)
        }

        // Display the formatted tip value on screen
        displayTip(tip)
    }

    /*
    * Format the tip amount to the local currency and display it onscreen.
    * Example: "Tip Amount: $3.00"*/
    private fun displayTip(tip: Double) {
        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
        binding.tvTipResult.text = getString(R.string.tip_amount, formattedTip)
    }

    // Key listener to hide the keyboard when the "Enter" key is pressed
    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            // Hide the keyboard
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }
}