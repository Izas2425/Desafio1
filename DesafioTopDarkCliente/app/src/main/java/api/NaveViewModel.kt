package api

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import modelo.Nave
import modelo.Usuario
import retrofit2.Response



class NaveViewModel : ViewModel() {
    private val _myResponse = MutableLiveData<Nave?>()
    val myResponse: LiveData<Nave?> get() = _myResponse

    private val _myResponseList = MutableLiveData<List<Nave>>()
    val myResponseList: LiveData<List<Nave>> get() = _myResponseList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _resOperacion = MutableLiveData<Boolean>()
    val resOperacion: LiveData<Boolean> get() = _resOperacion

    private val _errorCode = MutableLiveData<Int?>()
    val errorCode: LiveData<Int?> get() = _errorCode

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

    fun getNavesVM() {
        viewModelScope.launch {
            _isLoading.value = true
            var response: Response<MutableList<Nave>> = UserNetwork.retrofitNave.getNaves()

            if (response.isSuccessful) {
                _myResponseList.value = response.body()
            } else {
                _myResponseList.value = emptyList()
                _errorCode.value = response.code()
            }
            _isLoading.value = false
        }
    }

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

    fun deleteNavVM(matricula: String) {
        viewModelScope.launch {
            val response: Response<Boolean> = UserNetwork.retrofitNave.deleteNave(matricula)

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
    fun limpiarRespuesta (){
        _myResponse.value = null
    }

    fun limpiarError(){
        _errorCode.value = null
    }

}