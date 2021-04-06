package com.gustavoamorim.convidados.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.gustavoamorim.convidados.R
import com.gustavoamorim.convidados.service.constants.GuestConstants
import com.gustavoamorim.convidados.viewmodel.GuestFormViewModel
import kotlinx.android.synthetic.main.activity_guest_form.*

class GuestFormActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mGuestFormViewModel: GuestFormViewModel
    private var mGuestId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guest_form)

        mGuestFormViewModel = ViewModelProvider(this).get(GuestFormViewModel::class.java)

        setListeners()
        observe()
        loadData()

        radio_present.isChecked = true
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.button_save -> {
                val name = edit_name.text.toString()
                val presence = radio_present.isChecked

                mGuestFormViewModel.save(mGuestId, name, presence)
            }
        }
    }

    private fun loadData() {
        val bundle = intent.extras
        if (bundle != null) {
            mGuestId = bundle.getInt(GuestConstants.ID)
            mGuestFormViewModel.load(mGuestId)
        }
    }

    private fun setListeners() {
        button_save.setOnClickListener(this)
    }

    private fun observe() {
        mGuestFormViewModel.saveGuest.observe(this, {
            if (it) {
                Toast.makeText(applicationContext, "Sucesso", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(applicationContext, "Falha", Toast.LENGTH_SHORT).show()
            }
            finish()
        })

        mGuestFormViewModel.guest.observe(this, {
            edit_name.setText(it.name)
            radio_present.isChecked = it.presence
            radio_absent.isChecked = !it.presence
        })
    }

}