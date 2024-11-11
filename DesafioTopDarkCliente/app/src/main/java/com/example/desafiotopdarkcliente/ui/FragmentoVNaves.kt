package com.example.desafiotopdarkcliente.ui

import adaptadores.AdaptadorRvNaves
import adaptadores.AdaptadorRvPilotos
import android.annotation.SuppressLint
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import api.NaveViewModel
import api.UsuarioViewModel
import com.example.desafiotopdarkcliente.R
import com.example.desafiotopdarkcliente.databinding.FragmentFragmentoVNavesBinding
import com.example.desafiotopdarkcliente.databinding.FragmentFragmentoVPilotosBinding
import modelo.MostrarNave
import modelo.MostrarPiloto

class FragmentoVNaves : Fragment() {

    private var _binding: FragmentFragmentoVNavesBinding? = null
    private val binding get() = _binding!!

    private lateinit var naveViewModel: NaveViewModel
    // ViewModel
    private val fragmentoVNavesViewModel : FragmentoVNavesViewModel by viewModels()

    // Para la RV
    var datosRepresentar: ArrayList<MostrarNave> = ArrayList()
    lateinit var customAdapter: AdaptadorRvNaves


    companion object {
        fun newInstance() = FragmentoVNaves()
    }

    private val viewModel: FragmentoVNavesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        naveViewModel = ViewModelProvider(this)[NaveViewModel::class.java]

        naveViewModel.getNavesVM()
        naveViewModel.myResponseList.observe(this){naves ->
            if(!naves.isEmpty()){
                datosRepresentar.clear()
                for (nav in naves){
                    datosRepresentar.add(MostrarNave(nav.matricula.toString(), nav.tipo.toString()))
                }
                customAdapter.updateData((datosRepresentar))
            }
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFragmentoVNavesBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Inicializar el RecyclerView y el adaptador
        setupRecyclerView()

        fragmentoVNavesViewModel.myResponseList.observe(viewLifecycleOwner) { listaNaves ->
            datosRepresentar.clear()
            datosRepresentar.addAll(listaNaves)
            customAdapter.notifyDataSetChanged()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun setupRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(requireContext())
        binding.listaNavesRecycler.layoutManager = linearLayoutManager
        //Le pasamos también el viewModelMain para poder pasar datos desde el fragmento B a la activity.
        customAdapter = AdaptadorRvNaves( datosRepresentar, requireContext(), fragmentoVNavesViewModel )
        binding.listaNavesRecycler.adapter = customAdapter
    }

}