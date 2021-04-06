package com.gustavoamorim.motivation.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.gustavoamorim.motivation.R
import com.gustavoamorim.motivation.infra.MotivationConstants
import com.gustavoamorim.motivation.infra.SecurityPreferences
import com.gustavoamorim.motivation.repository.Mock
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mSecurityPreferences: SecurityPreferences
    private var mPhraseFilter = MotivationConstants.PHRASEFILTER.ALL

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        mSecurityPreferences = SecurityPreferences(this)
        val name = mSecurityPreferences.getString(MotivationConstants.KEY.NAME)
        textName.text = resources.getString(R.string.textName, name)

        buttonNewPhrase.setOnClickListener(this)
        imageAll.setOnClickListener(this)
        imageHappy.setOnClickListener(this)
        imageMorning.setOnClickListener(this)

        getNewPhrase()
    }

    override fun onClick(view: View) = when (view.id) {
        R.id.buttonNewPhrase -> getNewPhrase()
        R.id.imageAll,
        R.id.imageHappy,
        R.id.imageMorning -> handleFilterPhrases(view.id)
        else -> Unit
    }

    private fun handleFilterPhrases(id: Int) = when (id) {
        R.id.imageAll -> {
            mPhraseFilter = MotivationConstants.PHRASEFILTER.ALL
            imageAll.setColorFilter(ContextCompat.getColor(this, R.color.white))
            imageHappy.setColorFilter(ContextCompat.getColor(this, R.color.accent))
            imageMorning.setColorFilter(ContextCompat.getColor(this, R.color.accent))
        }
        R.id.imageHappy -> {
            mPhraseFilter = MotivationConstants.PHRASEFILTER.HAPPY
            imageAll.setColorFilter(ContextCompat.getColor(this, R.color.accent))
            imageHappy.setColorFilter(ContextCompat.getColor(this, R.color.white))
            imageMorning.setColorFilter(ContextCompat.getColor(this, R.color.accent))
        }
        R.id.imageMorning -> {
            mPhraseFilter = MotivationConstants.PHRASEFILTER.MORNING
            imageAll.setColorFilter(ContextCompat.getColor(this, R.color.accent))
            imageHappy.setColorFilter(ContextCompat.getColor(this, R.color.accent))
            imageMorning.setColorFilter(ContextCompat.getColor(this, R.color.white))
        }
        else -> Unit
    }

    private fun getNewPhrase() {
        val phrase = Mock().getPhrase(mPhraseFilter)
        textPhrase.text = phrase
    }

}