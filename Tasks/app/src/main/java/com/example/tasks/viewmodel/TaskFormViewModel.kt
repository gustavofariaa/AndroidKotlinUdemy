package com.example.tasks.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tasks.service.listener.APIListener
import com.example.tasks.service.listener.ValidationListener
import com.example.tasks.service.repository.PriorityRepository
import com.example.tasks.service.repository.TaskRepository
import com.example.tasks.service.repository.model.PriorityModel
import com.example.tasks.service.repository.model.TaskModel

class TaskFormViewModel(application: Application) : AndroidViewModel(application) {

    private val mPriorityRepository = PriorityRepository(application)
    private val mTaskRepository = TaskRepository(application)

    private val mPriorities = MutableLiveData<List<PriorityModel>>()
    var priorities: LiveData<List<PriorityModel>> = mPriorities

    private val mValidationListener = MutableLiveData<ValidationListener>()
    var validationListener: LiveData<ValidationListener> = mValidationListener

    private val mTask = MutableLiveData<TaskModel>()
    var task: LiveData<TaskModel> = mTask

    fun listPriorities() {
        mPriorities.value = mPriorityRepository.list()
    }

    fun save(task: TaskModel) {
        if (task.id == 0) {
            mTaskRepository.create(task, object : APIListener<Boolean> {
                override fun onSuccess(model: Boolean) {
                    mValidationListener.value = ValidationListener()
                }

                override fun onFailure(message: String) {
                    mValidationListener.value = ValidationListener(message)
                }
            })
        } else {
            mTaskRepository.update(task, object : APIListener<Boolean> {
                override fun onSuccess(model: Boolean) {
                    mValidationListener.value = ValidationListener()
                }

                override fun onFailure(message: String) {
                    mValidationListener.value = ValidationListener(message)
                }
            })
        }
    }

    fun load(taskId: Int) {
        mTaskRepository.load(taskId, object : APIListener<TaskModel> {
            override fun onSuccess(model: TaskModel) {
                mTask.value = model
            }

            override fun onFailure(message: String) {
                mValidationListener.value = ValidationListener(message)
            }
        })
    }

}