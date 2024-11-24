package com.example.desafiotopdarkcliente.ui

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
import modelo.Nave
import modelo.Vuelo
import retrofit2.Response


class FragmentoVMisionesViewModel : ViewModel() {

    private val _myResponseList = MutableLiveData<MostrarMision>()
    val myResponseList: LiveData<MostrarMision> get() = _myResponseList

    private val _myResponseM = MutableLiveData<Mision?>()
    val myResponseM: LiveData<Mision?> get() = _myResponseM

    private val _myResponseV = MutableLiveData<Vuelo?>()
    val myResponseV: LiveData<Vuelo?> get() = _myResponseV

    private val _myResponseC = MutableLiveData<Combate?>()
    val myResponseC: LiveData<Combate?> get() = _myResponseC

    private val _myResponseB = MutableLiveData<Bombardero?>()
    val myResponseB: LiveData<Bombardero?> get() = _myResponseB

    private val _myResponseDelete = MutableLiveData<Mision?>()
    val myResponseDelete: LiveData<Mision?> get() = _myResponseDelete

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

    fun getMisionVM(idmision: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            var response: Response<Mision> = UserNetwork.retrofitMision.getMision(idmision)

            if (response.isSuccessful) {
                _myResponseM.value = response.body()
            } else {
                _myResponseM.value = null
                _errorCode.value = response.code()
            }
            _isLoading.value = false
        }
    }

    fun getMisionDeleteVM(idmision: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            var response: Response<Mision> = UserNetwork.retrofitMision.getMision(idmision)

            if (response.isSuccessful) {
                _myResponseDelete.value = response.body()
            } else {
                _myResponseDelete.value = null
                _errorCode.value = response.code()
            }
            _isLoading.value = false
        }
    }

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

    fun deleteMisionVM(idmision: Int) {
        viewModelScope.launch {
            val response: Response<Boolean> = UserNetwork.retrofitMision.deleteMision(idmision)

            if (response.isSuccessful) {
                _resOperacion.value = response.body()
                _errorCode.value = response.code()
                obtenerTodasLasMisiones()
            } else {
                _resOperacion.value = false
                _errorCode.value = response.code()
            }
        }
    }

    fun deleteVueloVM(idmision: Int) {
        viewModelScope.launch {
            val response: Response<Boolean> = UserNetwork.retrofitMision.deleteVuelo(idmision)

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

    fun deleteCombateVM(idmision: Int) {
        viewModelScope.launch {
            val response: Response<Boolean> = UserNetwork.retrofitMision.deleteCombate(idmision)

            if (response.isSuccessful) {
                _resOperacion.value = response.body()
                _errorCode.value = response.code()
                obtenerTodosLosCombates()
            } else {
                _resOperacion.value = false
                _errorCode.value = response.code()
            }
        }
    }

    fun deleteBombarderoVM(idmision: Int) {
        viewModelScope.launch {
            val response: Response<Boolean> = UserNetwork.retrofitMision.deleteBombardero(idmision)

            if (response.isSuccessful) {
                _resOperacion.value = response.body()
                _errorCode.value = response.code()
                obtenerTodosLosBombarderos()
            } else {
                _resOperacion.value = false
                _errorCode.value = response.code()
            }
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