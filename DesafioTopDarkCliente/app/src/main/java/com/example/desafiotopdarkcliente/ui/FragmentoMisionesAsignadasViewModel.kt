package com.example.desafiotopdarkcliente.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import api.UserNetwork
import kotlinx.coroutines.launch
import modelo.Mision
import modelo.Misionasignada
import modelo.MostrarMision
import modelo.MostrarPiloto
import modelo.Usuario
import retrofit2.Response

class FragmentoMisionesAsignadasViewModel : ViewModel() {
    private val _myResponseList = MutableLiveData<List<MostrarMision>>()
    val myResponseList: MutableLiveData<List<MostrarMision>> get() = _myResponseList

    private val _myResponseListA = MutableLiveData<List<Misionasignada>>()
    val myResponseListA: MutableLiveData<List<Misionasignada>> get() = _myResponseListA

    private val _myResponse = MutableLiveData<Misionasignada>()
    val myResponse: LiveData<Misionasignada> get() = _myResponse

    private val _resOperacion = MutableLiveData<Boolean>()
    val resOperacion: LiveData<Boolean> get() = _resOperacion

    private val _errorCode = MutableLiveData<Int?>()
    val errorCode: LiveData<Int?> get() = _errorCode

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

      fun getMisionesAsignadasVM(id: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            var response: Response<MutableList<Misionasignada>> = UserNetwork.retrofitMisionAsignada.getMisionesasignadas(id)

            if (response.isSuccessful) {
                _myResponseListA.value = response.body()
            } else {
                _myResponseListA.value = emptyList()
                _errorCode.value = response.code()
            }
            _isLoading.value = false
        }
    }
}












