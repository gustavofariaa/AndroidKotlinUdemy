package com.example.tasks.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tasks.service.constants.TaskConstants
import com.example.tasks.service.listener.APIListener
import com.example.tasks.service.listener.ValidationListener
import com.example.tasks.service.repository.TaskRepository
import com.example.tasks.service.repository.model.TaskModel

class AllTasksViewModel(application: Application) : AndroidViewModel(application) {

    private val mTaskRepository = TaskRepository(application)

    private val mTasks = MutableLiveData<List<TaskModel>>()
    var tasks: LiveData<List<TaskModel>> = mTasks

    private val mValidationListener = MutableLiveData<ValidationListener>()
    var validationListener: LiveData<ValidationListener> = mValidationListener

    private var mTaskFilter = 0

    fun list(taskFilter: Int) {
        mTaskFilter = taskFilter

        val listener = object : APIListener<List<TaskModel>> {
            override fun onSuccess(model: List<TaskModel>) {
                mTasks.value = model
            }

            override fun onFailure(message: String) {
                mTasks.value = arrayListOf()
                mValidationListener.value = ValidationListener(message)
            }
        }

        when (mTaskFilter) {
            TaskConstants.FILTER.ALL -> mTaskRepository.all(listener)
            TaskConstants.FILTER.NEXT -> mTaskRepository.nextWeek(listener)
            TaskConstants.FILTER.EXPIRED -> mTaskRepository.overdue(listener)
        }

    }

    fun complete(id: Int) {
        mTaskRepository.complete(id, object : APIListener<Boolean> {
            override fun onSuccess(model: Boolean) = list(mTaskFilter)
            override fun onFailure(message: String) {}
        })
    }

    fun undo(id: Int) {
        mTaskRepository.undo(id, object : APIListener<Boolean> {
            override fun onSuccess(model: Boolean) = list(mTaskFilter)
            override fun onFailure(message: String) {}
        })
    }

    fun delete(id: Int) {
        mTaskRepository.delete(id, object : APIListener<Boolean> {
            override fun onSuccess(model: Boolean) {
                list(mTaskFilter)
                mValidationListener.value = ValidationListener()
            }

            override fun onFailure(message: String) {
                mValidationListener.value = ValidationListener(message)
            }
        })
    }

}