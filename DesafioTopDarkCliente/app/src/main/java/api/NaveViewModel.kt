package api

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import modelo.Nave
import retrofit2.Response
import api.NaveAPI


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



}