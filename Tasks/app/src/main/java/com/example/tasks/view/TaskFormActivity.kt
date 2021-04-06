package com.example.tasks.view

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.tasks.R
import com.example.tasks.service.constants.TaskConstants
import com.example.tasks.service.repository.model.TaskModel
import com.example.tasks.viewmodel.TaskFormViewModel
import kotlinx.android.synthetic.main.activity_register.button_save
import kotlinx.android.synthetic.main.activity_task_form.*
import java.text.SimpleDateFormat
import java.util.*

class TaskFormActivity : AppCompatActivity(), View.OnClickListener,
    DatePickerDialog.OnDateSetListener {

    private lateinit var mViewModel: TaskFormViewModel

    private val mDateFormart = SimpleDateFormat("dd/MM/yyyy")
    private val mListPriority: MutableList<Int> = arrayListOf()
    private val mDateFormat = SimpleDateFormat("dd/MM/yyyy")

    private var mTaskId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_form)

        mViewModel = ViewModelProvider(this).get(TaskFormViewModel::class.java)

        // Inicializa eventos
        listeners()
        observe()

        mViewModel.listPriorities()
        loadDataFromActivity()
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.button_save -> handleSave()
            R.id.button_date -> showDatePicker()
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)

        val date = mDateFormat.format(calendar.time)
        button_date.text = date
    }

    private fun loadDataFromActivity() {
        val bundle = intent.extras
        if (bundle != null) {
            mTaskId = bundle.getInt(TaskConstants.BUNDLE.TASKID)
            mViewModel.load(mTaskId)
            button_save.text = getString(R.string.update_task)
        }
    }

    private fun handleSave() {
        val task = TaskModel().apply {
            this.id = mTaskId
            this.description = edit_description.text.toString()
            this.complete = check_complete.isChecked
            this.dueDate = button_date.text.toString()
            this.priorityId = mListPriority[spinner_priority.selectedItemPosition]
        }

        mViewModel.save(task)
    }

    private fun showDatePicker() {
        val today = Calendar.getInstance()
        val year = today.get(Calendar.YEAR)
        val month = today.get(Calendar.MONTH)
        val dayOfMonth = today.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(this, this, year, month, dayOfMonth).show()
    }

    private fun observe() {
        mViewModel.priorities.observe(this, androidx.lifecycle.Observer {
            val list: MutableList<String> = arrayListOf()
            for (item in it) {
                list.add(item.description)
                mListPriority.add(item.id)
            }

            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, list)
            spinner_priority.adapter = adapter
        })

        mViewModel.task.observe(this, androidx.lifecycle.Observer {
            edit_description.setText(it.description)
            spinner_priority.setSelection(getPriorityIndex(it.priorityId))
            check_complete.isChecked = it.complete

            val date = SimpleDateFormat("yyyy-MM-dd").parse(it.dueDate)
            button_date.text = mDateFormart.format(date)
        })

        mViewModel.validationListener.observe(this, androidx.lifecycle.Observer {
            if (it.success()) {
                when (mTaskId) {
                    0 -> toast(getString(R.string.task_created))
                    else -> toast(getString(R.string.task_updated))
                }
                finish()
            } else {
                toast(it.message())
            }
        })

    }

    private fun toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun getPriorityIndex(priorityId: Int): Int {
        var priorityIndex = 0
        for (index in 0 until mListPriority.count()) {
            if (mListPriority[index] == priorityId) {
                priorityIndex = index
                break
            }
        }
        return priorityIndex
    }

    private fun listeners() {
        button_save.setOnClickListener(this)
        button_date.setOnClickListener(this)
    }


}
