package com.example.desafiotopdarkcliente.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import api.UserNetwork
import kotlinx.coroutines.launch
import modelo.MostrarNave
import modelo.Nave
import retrofit2.Response

class FragmentoNavesEnMisionesViewModel : ViewModel() {

    val naveSeleccionada = MutableLiveData<MostrarNave?>()

    private val _myResponseList = MutableLiveData<List<MostrarNave>>()
    val myResponseList: MutableLiveData<List<MostrarNave>> get() = _myResponseList

    private val _myResponseListN = MutableLiveData<List<Nave>>()
    val myResponseListN: MutableLiveData<List<Nave>> get() = _myResponseListN

    private val _resOperacion = MutableLiveData<Boolean>()
    val resOperacion: LiveData<Boolean> get() = _resOperacion

    private val _errorCode = MutableLiveData<Int?>()
    val errorCode: LiveData<Int?> get() = _errorCode

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _myResponse = MutableLiveData<Nave>()
    val myResponse: LiveData<Nave> get() = _myResponse

    fun getNavesVM() {
        viewModelScope.launch {
            _isLoading.value = true
            var response: Response<MutableList<Nave>> = UserNetwork.retrofitNave.getNaves()

            if (response.isSuccessful) {
                _myResponseListN.value = response.body()
            } else {
                _myResponseListN.value = emptyList()
                _errorCode.value = response.code()
            }
            _isLoading.value = false
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
}