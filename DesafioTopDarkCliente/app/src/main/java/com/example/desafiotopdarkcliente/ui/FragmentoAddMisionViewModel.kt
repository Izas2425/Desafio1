package com.example.desafiotopdarkcliente.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import api.UserNetwork
import kotlinx.coroutines.launch
import modelo.Bombardero
import modelo.Combate
import modelo.Mision
import modelo.MostrarMision
import modelo.Vuelo
import retrofit2.Response

class FragmentoAddMisionViewModel : ViewModel() {
    private val _myResponseList = MutableLiveData<MostrarMision>()
    val myResponseList: LiveData<MostrarMision> get() = _myResponseList

    private val _ultimoId = MutableLiveData<Int?>()
    val ultimoId: LiveData<Int?> get() = _ultimoId

    private val _myResponseListM = MutableLiveData<List<Mision>>()
    val myResponseListM: LiveData<List<Mision>> get() = _myResponseListM

    private val _myResponseListV = MutableLiveData<List<Vuelo>>()
    val myResponseListV: LiveData<List<Vuelo>> get() = _myResponseListV

    private val _myResponseListC = MutableLiveData<List<Combate>>()
    val myResponseListC: LiveData<List<Combate>> get() = _myResponseListC

    private val _myResponseListB = MutableLiveData<List<Bombardero>>()
    val myResponseListB: LiveData<List<Bombardero>> get() = _myResponseListB

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _resOperacion = MutableLiveData<Boolean>()
    val resOperacion: LiveData<Boolean> get() = _resOperacion

    private val _errorCode = MutableLiveData<Int?>()
    val errorCode: LiveData<Int?> get() = _errorCode

    fun addMisMV(mision: Mision){
        viewModelScope.launch {
            val response: Response<Boolean> = UserNetwork.retrofitMision.addMision(mision)
            //ultimaMisionMv()

            if (response.isSuccessful) {
                _resOperacion.value = response.body()
                _errorCode.value = response.code()

                obtenerTodasLasMisiones()
                ultimaMisionMv()
                Log.e("Izaskun", "ultima mision en addMisMv ${_ultimoId.value}")
            } else {
                _resOperacion.value = false
                _errorCode.value = response.code()
                Log.e("Izaskun", "code error en addMisMv ${_errorCode.value}")
            }
        }
    }

    fun addVueMV(vuelo: Vuelo){
        viewModelScope.launch {
            val response: Response<Boolean> = UserNetwork.retrofitMision.addVuelo(vuelo)

            if (response.isSuccessful) {
                _resOperacion.value = response.body()
                _errorCode.value = response.code()
                obtenerTodosLosVuelos()
            } else {
                _resOperacion.value = false
                _errorCode.value = response.code()
            }
        }
    }

    fun addComMV(combate: Combate){
        viewModelScope.launch {
            val response: Response<Boolean> = UserNetwork.retrofitMision.addCombate(combate)

            if (response.isSuccessful) {
                _resOperacion.value = response.body()
                _errorCode.value = response.code()
                obtenerTodosLosVuelos()
            } else {
                _resOperacion.value = false
                _errorCode.value = response.code()
            }
        }
    }

    fun addBomMV(bombardero: Bombardero){
        viewModelScope.launch {
            val response: Response<Boolean> = UserNetwork.retrofitMision.addBombardero(bombardero)

            if (response.isSuccessful) {
                _resOperacion.value = response.body()
                _errorCode.value = response.code()

                obtenerTodosLosVuelos()
            } else {
                _resOperacion.value = false
                _errorCode.value = response.code()
            }
        }
    }

    fun ultimaMisionMv(){
        viewModelScope.launch {
            val response: Response<Int> = UserNetwork.retrofitMision.getUltimoId()

            if (response.isSuccessful) {
                val ultimoId = response.body()
                _ultimoId.value = ultimoId
                _resOperacion.value = true
               // ultimaMisionMv()
            } else {
                _ultimoId.value = null
                _resOperacion.value = false
            }
            _errorCode.value = response.code()
        }
    }



    fun obtenerTodasLasMisiones(){
        viewModelScope.launch {
            _myResponseListM.value = UserNetwork.retrofitMision.getMisiones().body()
        }
    }

    fun obtenerTodosLosVuelos(){
        viewModelScope.launch {
            _myResponseListV.value = UserNetwork.retrofitMision.getVuelos().body()
        }
    }

    fun obtenerTodosLosCombates(){
        viewModelScope.launch {
            _myResponseListC.value = UserNetwork.retrofitMision.getCombates().body()
        }
    }

    fun obtenerTodosLosBombarderos(){
        viewModelScope.launch {
            _myResponseListB.value = UserNetwork.retrofitMision.getBombarderos().body()
        }
    }
}