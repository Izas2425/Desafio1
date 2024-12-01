package com.example.desafiotopdarkcliente.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import api.UserNetwork
import kotlinx.coroutines.launch
import modelo.Mision
import modelo.MostrarMision
import retrofit2.Response


class FragmentoMisionEnAsignamisionViewModel : ViewModel() {

    val misionSeleccionada = MutableLiveData<MostrarMision?>()

    private val _myResponseList = MutableLiveData<List<MostrarMision>>()
    val myResponseList: MutableLiveData<List<MostrarMision>> get() = _myResponseList

    private val _myResponseListM = MutableLiveData<List<Mision>>()
    val myResponseListM: MutableLiveData<List<Mision>> get() = _myResponseListM

    private val _resOperacion = MutableLiveData<Boolean>()
    val resOperacion: LiveData<Boolean> get() = _resOperacion

    private val _errorCode = MutableLiveData<Int?>()
    val errorCode: LiveData<Int?> get() = _errorCode

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _myResponse = MutableLiveData<Mision>()
    val myResponse: LiveData<Mision> get() = _myResponse

    fun getMisionesVM() {
        viewModelScope.launch {
            _isLoading.value = true
            var response: Response<MutableList<Mision>> = UserNetwork.retrofitMision.getMisiones()

            if (response.isSuccessful) {
                _myResponseListM.value = response.body()
            } else {
                _myResponseListM.value = emptyList()
                _errorCode.value = response.code()
            }
            _isLoading.value = false
        }
    }

    fun getMisionVM(idmision: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            var response: Response<Mision> = UserNetwork.retrofitMision.getMision(idmision)

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