package com.gustavoamorim.motivation.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.gustavoamorim.motivation.R
import com.gustavoamorim.motivation.infra.MotivationConstants
import com.gustavoamorim.motivation.infra.SecurityPreferences
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mSecurityPreferences: SecurityPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        supportActionBar?.hide()

        mSecurityPreferences = SecurityPreferences(this)

        verifyName()

        buttonSave.setOnClickListener(this)
    }

    private fun verifyName() {
        val name = mSecurityPreferences.getString(MotivationConstants.KEY.NAME)
        if (name != "") navigateToMainActivity()
    }

    override fun onClick(view: View) = when (view.id) {
        R.id.buttonSave -> handleSave()
        else -> Unit
    }

    private fun handleSave() {
        val name = editName.text.toString()

        if (name != "") {
            mSecurityPreferences.storeString(MotivationConstants.KEY.NAME, name)
            navigateToMainActivity()
        } else Toast.makeText(this, "Informe seu nome", Toast.LENGTH_SHORT).show()
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}