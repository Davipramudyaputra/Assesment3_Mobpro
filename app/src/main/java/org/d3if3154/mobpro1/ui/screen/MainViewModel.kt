package org.d3if3154.mobpro1.ui.screen

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.d3if3154.mobpro1.Network.ApiStatus
import org.d3if3154.mobpro1.Network.HewanApi
import org.d3if3154.mobpro1.model.Hewan

class MainViewModel : ViewModel() {
    var data = mutableStateOf(emptyList<Hewan>())
        private set
    var status = MutableStateFlow(ApiStatus.LOADING)
        private set
    init {
        retrieveData()
    }

    fun retrieveData() {
        viewModelScope.launch(Dispatchers.IO) {
            status.value = ApiStatus.LOADING
            try {
                data.value = HewanApi.service.getHewan()
                status.value = ApiStatus.SUCCESS
            } catch (e: Exception) {
                Log.d("MainViewModel", "Failure: ${e.message}")
                status.value = ApiStatus.FAILED
            }
        }
    }
}