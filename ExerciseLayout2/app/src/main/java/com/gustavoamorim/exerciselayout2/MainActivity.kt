package com.gustavoamorim.exerciselayout2

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkColor.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.checkColor -> changeIconColor()
        }
    }

    private fun changeIconColor() {
        if (checkColor.isChecked) androidIcon.setColorFilter(
            ContextCompat.getColor(this, R.color.design_default_color_secondary)
        )
        else androidIcon.setColorFilter(
            ContextCompat.getColor(this, R.color.design_default_color_primary)
        )
    }
}