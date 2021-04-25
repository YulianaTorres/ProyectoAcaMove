package com.example.acamove.ui.notifications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NotificationsViewModel : ViewModel() {
//Notifications es Puntos de Interes
    private val _text = MutableLiveData<String>().apply {
        value = "Atractivos turísticos que te pueden interesar"
    }
    val text: LiveData<String> = _text
}