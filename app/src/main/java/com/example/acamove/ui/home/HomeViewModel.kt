package com.example.acamove.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {
//Home es Rutas
    private val _text = MutableLiveData<String>().apply {
        value = "Rutas"
    }
    val text: LiveData<String> = _text
}