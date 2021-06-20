package com.udacity.shoestore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class MainViewModel : ViewModel() {

    var isLogging = false
    private var _isLoggedIn = MutableLiveData(false)
    val isLoggedIn: LiveData<Boolean> get() = _isLoggedIn

    private var _shoeList = MutableLiveData<MutableList<String>>(mutableListOf())
    val shoeList: LiveData<MutableList<String>> get() = _shoeList

    private var _closeShoeDetailEvent = MutableLiveData(false)
    val closeShoeDetailEvent: LiveData<Boolean> get() = _closeShoeDetailEvent

    var shoeName = ""
    var company = ""
    var shoeSize = ""
    var description = ""

    private fun clearFields() {
        shoeName = ""
        company = ""
        shoeSize = ""
        description = ""
    }

    fun addShoe() {
        val shoeText =
            "${shoeName.toUpperCase(Locale.ROOT)}\nCompany: $company, Size: $shoeSize, Description: $description"
        _shoeList.value?.add(shoeText)
        closeShoeDetail()
    }

    fun closeShoeDetail() {
        clearFields()
        _closeShoeDetailEvent.value = true
    }

    fun onCloseShoeDetailEventComplete() {
        _closeShoeDetailEvent.value = false
    }

    fun doLogin() {
        isLogging = false
        _isLoggedIn.value = true
    }
}