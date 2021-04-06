package com.example.tasks.service.listener

import com.example.tasks.service.repository.model.HeaderModel

interface APIListener<T> {

    fun onSuccess(model: T) {}

    fun onFailure(message: String) {}

}