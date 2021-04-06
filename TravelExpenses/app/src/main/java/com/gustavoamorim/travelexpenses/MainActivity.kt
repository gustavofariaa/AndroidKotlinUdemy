package com.gustavoamorim.travelexpenses

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.NumberFormatException

class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonCalculate.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.buttonCalculate -> calculate();
        }
    }

    private fun calculate() {
        if (validation()) {
            try {
                val distance = editDistance.text.toString().toFloat()
                val price = editPrice.text.toString().toFloat()
                val autonomy = editAutonomy.text.toString().toFloat()

                val amount = (distance * price) / autonomy
                textAmount.text = "R$ ${"%.2f".format(amount)}"
            } catch (exception: NumberFormatException) {
                Toast.makeText(this, "Informe valores válidos", Toast.LENGTH_LONG).show()
            }
            return;
        }
        Toast.makeText(this, "Por favor, preencha todos os campos", Toast.LENGTH_LONG).show()
    }

    private fun validation() = (
            editDistance.text.toString() != "" ||
            editPrice.text.toString() != "" ||
            editAutonomy.text.toString() != "")

}