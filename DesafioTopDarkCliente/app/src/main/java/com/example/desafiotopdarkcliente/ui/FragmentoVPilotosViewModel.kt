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

class FragmentoVPilotosViewModel : ViewModel() {
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

    private val _updateResponse = MutableLiveData<Boolean>()
    val updateResponse: LiveData<Boolean> get() = _updateResponse



    fun addUsuario(piloto: MostrarPiloto) {
        val currentList = _myResponseList.value.orEmpty().toMutableList()
        currentList.add(piloto)
        _myResponseList.value = currentList
    }

    fun delUsuario(piloto: MostrarPiloto) {
        val currentList = _myResponseList.value.orEmpty().toMutableList()
        currentList.remove(piloto)
        _myResponseList.value = currentList
    }

    fun setPilotos(pilotos: ArrayList<MostrarPiloto>){
        _myResponseList.value = pilotos
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


    fun deletePilotoVM(piloto: MostrarPiloto){
        viewModelScope.launch {
            val response: Response<Boolean> = UserNetwork.retrofit.deleteUsuario(piloto.id!!)

            if (response.isSuccessful) {
                _resOperacion.value = response.body()
                _errorCode.value = response.code()
                getPilotosVM()

            } else {
                _resOperacion.value = false
                _errorCode.value = response.code()
            }
        }
    }
    fun getPilotosVM() {
        viewModelScope.launch {
            _isLoading.value = true
            var response: Response<MutableList<Usuario>> = UserNetwork.retrofit.getPilotos()

            if (response.isSuccessful) {
                _myResponseListP.value = response.body()
            } else {
                _myResponseListP.value = emptyList()
                _errorCode.value = response.code()
            }
            _isLoading.value = false
        }
    }

    fun updateUsuarioVM(id: Int, usuario: Usuario){
        viewModelScope.launch {
            _isLoading.value = true
            val response: Response<Boolean> = UserNetwork.retrofit.updateUsuario(id, usuario)

            if (response.isSuccessful){
                _updateResponse.value = response.body() ?: false
            }else{
                _updateResponse.value = false
                _errorCode.value = response.code()
            }
            _isLoading.value = false
        }
    }


}