package com.example.desafiotopdarkcliente.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import api.UserNetwork
import kotlinx.coroutines.launch
import modelo.MostrarPiloto
import modelo.Usuario
import retrofit2.Response

class FragmentoRankingPilotosViewModel : ViewModel() {
    private val _myResponseList = MutableLiveData<List<MostrarPiloto>>()
    val myResponseList: MutableLiveData<List<MostrarPiloto>> get() = _myResponseList

    private val _myResponseListP = MutableLiveData<List<Usuario>>()
    val myResponseListP: MutableLiveData<List<Usuario>> get() = _myResponseListP

    private val _myResponse = MutableLiveData<Usuario>()
    val myResponse: LiveData<Usuario> get() = _myResponse

    private val _resOperacion = MutableLiveData<Boolean>()
    val resOperacion: LiveData<Boolean> get() = _resOperacion

    private val _errorCode = MutableLiveData<Int?>()
    val errorCode: LiveData<Int?> get() = _errorCode

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun getPilotosRankingVM() {
        viewModelScope.launch {
            _isLoading.value = true
            var response: Response<MutableList<Usuario>> = UserNetwork.retrofit.getPilotosRanking()

            if (response.isSuccessful) {
                _myResponseListP.value = response.body()
            } else {
                _myResponseListP.value = emptyList()
                _errorCode.value = response.code()
            }
            _isLoading.value = false
        }
    }

    fun getUsuarioVM(id: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            var response: Response<Usuario> = UserNetwork.retrofit.getUsuario(id)

            if (response.isSuccessful) {
                _myResponse.value = response.body()
            } else {
                _myResponse.value = null
                _errorCode.value = response.code()
            }
            _isLoading.value = false
        }
    }
}