package com.example.foody.ui.fragments.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ConfirmViewModel :ViewModel (){
    var confirm = MutableLiveData<Boolean>()
}