package com.example.miloav.screens.Home.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.miloav.model.Event
import com.example.miloav.network.EventListener
import com.example.miloav.network.EventService

class HomeViewModel : ViewModel(), EventListener {

    private val service = EventService(this)
    private val _eventsList = MutableLiveData<MutableList<Event>>().apply {
        value = mutableListOf<Event>()
    }
    val eventsList: LiveData<MutableList<Event>> = _eventsList

    private val _isError = MutableLiveData<Boolean>().apply {
        value = false
    }
    val isError: LiveData<Boolean> = _isError

    fun events() {
        service.fetchEvents()
    }

    override fun onSuccessEventListListener(eventList: MutableList<Event>) {
        _eventsList.value = eventList
    }
    override fun onFailureListener(isError: Boolean) {
        _isError.value = isError
    }
}