package com.expert.apps.utils.base

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.expert.apps.utils.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

abstract class BaseViewModel  : ViewModel() {


    val coroutinesScope = CoroutineScope(Dispatchers.Default + Job())
    val errorMessage: MutableLiveData<String> = MutableLiveData()


    fun launch(function: suspend () -> Unit) {
        coroutinesScope.launch {
            try {
                function.invoke()
            } catch (e: Exception) {
                Log.d("TAG", "coroutinesScopeBaseViewModelError  : $e")
                errorMessage.postValue(Constants.System_error)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()



    }

}