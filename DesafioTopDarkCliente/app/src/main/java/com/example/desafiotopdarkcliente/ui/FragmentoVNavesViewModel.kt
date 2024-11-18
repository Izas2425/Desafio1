package com.example.desafiotopdarkcliente.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import api.UserNetwork
import kotlinx.coroutines.launch
import modelo.MostrarNave
import modelo.MostrarPiloto
import modelo.Nave
import modelo.Usuario
import retrofit2.Response

class FragmentoVNavesViewModel : ViewModel() {
    private val _myResponseList = MutableLiveData<List<MostrarNave>>()
    val myResponseList: MutableLiveData<List<MostrarNave>> get() = _myResponseList

    private val _myResponse = MutableLiveData<Nave>()
    val myResponse: LiveData<Nave> get() = _myResponse

    private val _resOperacion = MutableLiveData<Boolean>()
    val resOperacion: LiveData<Boolean> get() = _resOperacion

    private val _errorCode = MutableLiveData<Int?>()
    val errorCode: LiveData<Int?> get() = _errorCode

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun addUsuario(nave: MostrarNave) {
        val currentList = _myResponseList.value.orEmpty().toMutableList()
        currentList.add(nave)
        _myResponseList.value = currentList
    }

    fun delUsuario(nave: MostrarNave) {
        val currentList = _myResponseList.value.orEmpty().toMutableList()
        currentList.remove(nave)
        _myResponseList.value = currentList
    }

    fun setPilotos(naves: ArrayList<MostrarNave>){
        _myResponseList.value = naves
    }

    fun deleteNavVM(matricula: String) {
        viewModelScope.launch {
            val response: Response<Boolean> = UserNetwork.retrofitNave.deleteNave(matricula)

            if (response.isSuccessful) {
                _resOperacion.value = response.body()
                _errorCode.value = response.code()
               // obtenerTodasLasNaves()
            } else {
                _resOperacion.value = false
                _errorCode.value = response.code()
            }
        }
    }
    fun getNaveVM(matricula: String) {
        viewModelScope.launch {
            _isLoading.value = true
            var response: Response<Nave> = UserNetwork.retrofitNave.getNave(matricula)

            if (response.isSuccessful) {
                _myResponse.value = response.body()
            } else {
                _myResponse.value = null
                _errorCode.value = response.code()
            }
            _isLoading.value = false
        }
    }



    fun limpiarRespuesta (){
        _myResponse.value = null
    }

    fun limpiarError(){
        _errorCode.value = null
    }

}