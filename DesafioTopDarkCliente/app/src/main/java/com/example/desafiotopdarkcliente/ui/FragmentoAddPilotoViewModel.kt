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

class FragmentoAddPilotoViewModel : ViewModel() {
    private val _myResponseList = MutableLiveData<List<Usuario>>()
    val myResponseList: MutableLiveData<List<Usuario>> get() = _myResponseList

    private val _myResponse = MutableLiveData<Usuario>()
    val myResponse: LiveData<Usuario> get() = _myResponse

    private val _resOperacion = MutableLiveData<Boolean>()
    val resOperacion: LiveData<Boolean> get() = _resOperacion

    private val _errorCode = MutableLiveData<Int?>()
    val errorCode: LiveData<Int?> get() = _errorCode

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun addUserVM(user: Usuario) {
        viewModelScope.launch {
            val response: Response<Boolean> = UserNetwork.retrofit.addUsuario(user)

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