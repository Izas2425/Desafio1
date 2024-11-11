package com.example.desafiotopdarkcliente.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import modelo.MostrarNave
import modelo.MostrarPiloto
import modelo.Usuario

class FragmentoVNavesViewModel : ViewModel() {
    private val _myResponseList = MutableLiveData<List<MostrarNave>>()
    val myResponseList: MutableLiveData<List<MostrarNave>> get() = _myResponseList

    private val _myResponse = MutableLiveData<Usuario>()
    val myResponse: LiveData<Usuario> get() = _myResponse

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

}