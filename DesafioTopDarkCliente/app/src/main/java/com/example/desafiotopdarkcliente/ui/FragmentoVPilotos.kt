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
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import api.UsuarioViewModel
import com.example.desafiotopdarkcliente.MainViewModel
import com.example.desafiotopdarkcliente.R
import com.example.desafiotopdarkcliente.databinding.FragmentFragmentoVPilotosBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import modelo.MostrarPiloto

class FragmentoVPilotos : Fragment() {
    private var _binding: FragmentFragmentoVPilotosBinding? = null
    private val binding get() = _binding!!

    private lateinit var navController: NavController


    private lateinit var mainViewModel: MainViewModel
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
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]

        mainViewModel.getPilotosVM()
        mainViewModel.myResponseList.observe(this) {pilotos ->
            if (!pilotos.isEmpty()){
                datosRepresentar.clear()
                for (user in pilotos) {
                    Log.d("Izaskun", user.toString())
                    datosRepresentar.add(MostrarPiloto( user.id, user.nombre.toString(), user.nivel.toString()))
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



        val fabAddPiloto: FloatingActionButton = view.findViewById(R.id.fabAddPiloto)
        fabAddPiloto.setOnClickListener{
            findNavController().navigate(R.id.action_VPilotos_to_AddPiloto)
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