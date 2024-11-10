package com.example.desafiotopdarkcliente.ui

import adaptadores.AdaptadorRvPilotos
import android.annotation.SuppressLint
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import api.UsuarioViewModel
import com.example.desafiotopdarkcliente.R
import com.example.desafiotopdarkcliente.databinding.FragmentFragmentoVPilotosBinding
import modelo.MostrarPiloto

class FragmentoVPilotos : Fragment() {
    private var _binding: FragmentFragmentoVPilotosBinding? = null
    private val binding get() = _binding!!

    private lateinit var usuarioViewModel: UsuarioViewModel
    // ViewModel
    private val fragmentoVPilotosViewModel : FragmentoVPilotosViewModel by viewModels()

    // Para la RV
    var datosRepresentar: ArrayList<MostrarPiloto> = ArrayList()
    lateinit var customAdapter: AdaptadorRvPilotos

    companion object {
        fun newInstance() = FragmentoVPilotos()

    }

    private val viewModel: FragmentoVPilotosViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        usuarioViewModel = ViewModelProvider(this)[UsuarioViewModel::class.java]

        usuarioViewModel.getPilotosVM()
        usuarioViewModel.myResponseList.observe(this) {pilotos ->
            if (!pilotos.isEmpty()){
                datosRepresentar.clear()
                for (user in pilotos) {
                    Log.d("Izaskun", user.toString())
                    datosRepresentar.add(MostrarPiloto(user.nombre.toString(), user.nivel.toString()))
                }
                customAdapter.updateData(datosRepresentar)
            }

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFragmentoVPilotosBinding.inflate(inflater, container, false)
       val root: View = binding.root
        return root

        }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Inicializar el RecyclerView y el adaptador
        setupRecyclerView()

        fragmentoVPilotosViewModel.myResponseList.observe(viewLifecycleOwner) { listaPilotos ->
            datosRepresentar.clear()
            datosRepresentar.addAll(listaPilotos)
            customAdapter.notifyDataSetChanged()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(requireContext())
        binding.listaPilotosRecycler.layoutManager = linearLayoutManager
        //Le pasamos también el viewModelMain para poder pasar datos desde el fragmento B a la activity.
        customAdapter = AdaptadorRvPilotos( datosRepresentar, requireContext(), fragmentoVPilotosViewModel )
        binding.listaPilotosRecycler.adapter = customAdapter
    }
}