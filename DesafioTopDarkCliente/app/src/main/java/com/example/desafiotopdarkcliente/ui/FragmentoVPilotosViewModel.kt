package com.example.desafiotopdarkcliente.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import modelo.MostrarPiloto
import modelo.Usuario

class FragmentoVPilotosViewModel : ViewModel() {
    private val _myResponseList = MutableLiveData<List<MostrarPiloto>>()
    val myResponseList: MutableLiveData<List<MostrarPiloto>> get() = _myResponseList

    private val _myResponse = MutableLiveData<Usuario>()
    val myResponse: LiveData<Usuario> get() = _myResponse

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

}