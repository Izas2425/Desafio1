package com.example.desafiotopdarkcliente.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import api.UserNetwork
import kotlinx.coroutines.launch
import modelo.Misionasignada
import retrofit2.Response

class FragmentoAsignarMisionViewModel : ViewModel() {

    private val _myResponseList = MutableLiveData<Misionasignada>()
    val myResponseList: LiveData<Misionasignada> get() = _myResponseList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _resOperacion = MutableLiveData<Boolean>()
    val resOperacion: LiveData<Boolean> get() = _resOperacion

    private val _errorCode = MutableLiveData<Int?>()
    val errorCode: LiveData<Int?> get() = _errorCode

    fun addVM(misionasignada: Misionasignada){
        viewModelScope.launch {
            val response: Response<Boolean> = UserNetwork.retrofitMisionAsignada.addMisionasignada(misionasignada)
            Log.d("Izaskun", "Asignando misión (addVM): $misionasignada")

            if (response.isSuccessful) {
                _resOperacion.value = response.body()
                _errorCode.value = response.code()


            } else {
                _resOperacion.value = false
                _errorCode.value = response.code()

            }
        }
    }
}