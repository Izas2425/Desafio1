package com.example.desafiotopdarkcliente.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import api.UserNetwork
import kotlinx.coroutines.launch
import modelo.Nave
import modelo.Usuario
import retrofit2.Response

class FragmentoAddNaveViewModel : ViewModel() {
    private val _myResponseList = MutableLiveData<List<Nave>>()
    val myResponseList: MutableLiveData<List<Nave>> get() = _myResponseList

    private val _myResponse = MutableLiveData<Nave>()
    val myResponse: LiveData<Nave> get() = _myResponse

    private val _resOperacion = MutableLiveData<Boolean>()
    val resOperacion: LiveData<Boolean> get() = _resOperacion

    private val _errorCode = MutableLiveData<Int?>()
    val errorCode: LiveData<Int?> get() = _errorCode

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading


    fun addNavVM(nav: Nave) {
        viewModelScope.launch {
            val response: Response<Boolean> = UserNetwork.retrofitNave.addNave(nav)

            if (response.isSuccessful) {
                _resOperacion.value = response.body()
                _errorCode.value = response.code()
                obtenerTodasLasNaves()
            } else {
                _resOperacion.value = false
                _errorCode.value = response.code()
            }
        }
    }

    fun obtenerTodasLasNaves(){
        viewModelScope.launch {
            _myResponseList.value = UserNetwork.retrofitNave.getNaves().body()
        }
    }

}