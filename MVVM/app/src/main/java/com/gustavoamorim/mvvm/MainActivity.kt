package com.gustavoamorim.mvvm

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    @SuppressLint("ShowToast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.textTitle.observe(this, Observer {
            textTitle.text = it
        })

        viewModel.login.observe(this, Observer {
            if (it) Toast.makeText(
                applicationContext, "Success!", Toast.LENGTH_SHORT
            ).show()
            else Toast.makeText(
                applicationContext, "Fail!", Toast.LENGTH_SHORT
            ).show()
        })

        buttonLogin.setOnClickListener {
            val login = editLogin.text.toString()
            viewModel.login(login)
        }
    }
}