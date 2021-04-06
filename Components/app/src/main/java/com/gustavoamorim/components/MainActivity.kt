package com.gustavoamorim.components

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.gustavoamorim.components.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener, AdapterView.OnItemSelectedListener,
    SeekBar.OnSeekBarChangeListener, CompoundButton.OnCheckedChangeListener,
    DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private lateinit var binding: ActivityMainBinding

    private val mSimpleDateFormat = SimpleDateFormat("dd/MM/yyyy")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonToast.setOnClickListener(this)
        binding.buttonCustomToast.setOnClickListener(this)

        binding.buttonSnack.setOnClickListener(this)
        binding.buttonSnackAction.setOnClickListener(this)

        binding.buttonDatePicker.setOnClickListener(this)

        binding.buttonTimePicker.setOnClickListener(this)

        binding.spinnerStatic.onItemSelectedListener = this
        binding.buttonGetSpinner.setOnClickListener(this)
        binding.buttonSetSpinner.setOnClickListener(this)

        loadSpinner()

        binding.seekBar.setOnSeekBarChangeListener(this)
        binding.buttonGetSeekBar.setOnClickListener(this)
        binding.buttonSetSeekBar.setOnClickListener(this)

        binding.switchOnOff.setOnCheckedChangeListener(this)
        binding.checkBoxOnOff.setOnCheckedChangeListener(this)
        binding.radioButtonOn.setOnCheckedChangeListener(this)
        binding.radioButtonOff.setOnCheckedChangeListener(this)
    }

    override fun onClick(view: View) = when (view.id) {
        R.id.button_toast -> toast("Toast")
        R.id.button_custom_toast -> customToast()
        R.id.button_snack -> snack("Snackbar")
        R.id.button_snack_action -> snackAction()
        R.id.button_date_picker -> datePicker()
        R.id.button_time_picker -> timePicker()
        R.id.button_get_spinner -> getSpinnerValue()
        R.id.button_set_spinner -> setSpinnerValue()
        R.id.button_get_seek_bar -> getSeekBarValue()
        R.id.button_set_seek_bar -> setSeekBarValue()
        else -> {
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val date = Calendar.getInstance()
        date.set(year, month, dayOfMonth)
        binding.buttonDatePicker.text = mSimpleDateFormat.format(date.time)
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        binding.buttonTimePicker.text = "${hourOfDay}h:${minute}m"
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) =
        when (parent?.id) {
            R.id.spinner_static -> {
                val value = parent.getItemAtPosition(position).toString()
                toast(value)
            }
            else -> {
            }
        }

    override fun onNothingSelected(parent: AdapterView<*>?) = toast("Nothing selected")

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) =
        when (seekBar?.id) {
            R.id.seek_bar -> {
                binding.seekBarValue.text = "Seek bar value: $progress"
            }
            else -> {
            }
        }

    override fun onStartTrackingTouch(seekBar: SeekBar?) = toast("Track started")

    override fun onStopTrackingTouch(seekBar: SeekBar?) = toast("Track stopped")

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) =
        when (buttonView?.id) {
            R.id.switch_on_off -> toast("Switch ${if (isChecked) "enabled" else "disabled"}")
            R.id.check_box_on_off -> toast("Checkbox ${if (isChecked) "enabled" else "disabled"}")
            R.id.radio_button_on -> toast("Radio button on: ${if (isChecked) "enabled" else "disabled"}")
            R.id.radio_button_off -> toast("Radio button off: ${if (isChecked) "enabled" else "disabled"}")
            else -> {
            }
        }

    private fun toast(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

    private fun customToast() {
        val message = "Custom toast"
        val toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.TOP, 0, 250)
        val toastLayout = layoutInflater.inflate(R.layout.toast_layout, null)
        val toastText = toastLayout.findViewById<TextView>(R.id.text_toast)
        toastText.text = message
        toast.view = toastLayout
        toast.show()
    }

    private fun snack(message: String) =
        Snackbar.make(binding.linearRoot, message, Snackbar.LENGTH_SHORT).show()

    private fun snackAction() {
        val snack = Snackbar.make(binding.linearRoot, "Snackbar with action", Snackbar.LENGTH_SHORT)
        snack.setAction("Undo", View.OnClickListener { toast("Undone!") })
        snack.show()
    }

    private fun datePicker() {
        val calendar = Calendar.getInstance()
        DatePickerDialog(
            this,
            this,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun timePicker() {
        TimePickerDialog(this, this, 1, 1, true).show()
    }

    private fun getSpinnerValue() {
        val selectedItem = binding.spinnerStatic.selectedItem
        val selectedItemPosition = binding.spinnerStatic.selectedItemPosition
//        val selectedItemId = binding.spinnerStatic.selectedItemId
        val message = "$selectedItem at position $selectedItemPosition"
        snack(message)
    }

    private fun setSpinnerValue() = binding.spinnerStatic.setSelection(2)

    private fun loadSpinner() {
        val mList = listOf("ounce (oz)", "pound (lb)", "quarter (qt)", "ton (t)")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, mList)
        binding.spinnerDynamic.adapter = adapter
    }

    private fun getSeekBarValue() = toast("Seek bar value: ${binding.seekBar.progress}")

    private fun setSeekBarValue() {
        binding.seekBar.progress = 20
    }

}